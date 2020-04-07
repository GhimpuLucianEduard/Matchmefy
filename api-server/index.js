const dotenv = require('dotenv')
const express = require('express')
const http = require('http')
const morgan = require('morgan')
const bodyParser = require('body-parser')
const router = express.Router()
const mongoose = require('mongoose')
const User = require('./models/User.js')
const Artist = require('./models/Artist.js')
const Album = require('./models/Album.js')
const Track = require('./models/Track.js')

const app = express()
const port = process.env.PORT || 8080
const server = http.createServer(app)

dotenv.config({ debug: process.env.DEBUG })

app.use(bodyParser.json({limit: "50mb"}));
app.use(morgan('dev'))
app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())

const dbUser = process.env.DB_USER
const dbPass = process.env.DB_PASSWORD
const dbName = process.env.DB_DATABASE
const uri = `mongodb+srv://${dbUser}:${dbPass}@${dbName}/test?retryWrites=true&w=majority`
console.log(uri)

mongoose.connect(uri)
.then(()=>{
    console.log("Connected to Atlas")
}).catch(err => {
    console.log("Failed to connect to Atlas")
    console.log(err)
})

const postTest = router.post('/', async (req, res, next) => {

    const user = new User(req.body.user);
    user._id = req.body.user.id

    const artistUpsertOperations = []
    const albumUpsertOperations = []
    const trackUpsertOperation = []

    // For each artist, add it in the Bulk Upsert Array
    // Also add the artist to the user's artist array
    req.body.artists.forEach(rawArtist => {

        const artist = new Artist(rawArtist)
        artist._id = rawArtist.id
        user.artists.push(rawArtist.id)

        const upsertArtist = {
            'updateOne': {
              'filter': { '_id': rawArtist.id },
              'update': artist,
              'upsert': true
            }
        };
        
        artistUpsertOperations.push(upsertArtist)
    });

    // For each track, save album & artists
    // Also save the track id in the user's tracks list
    req.body.tracks.forEach(rawTrack => {

        const track = new Track(rawTrack)
        track._id = rawTrack.id
        user.tracks.push(rawTrack.id)

        // Album
        const album = new Album(rawTrack.album)
        album._id = rawTrack.album.id
        track.album = rawTrack.album.id

        // For each artist in the album, add it to the Bulk Upsert Array
        // Also push the artist to the album's artist array
        rawTrack.album.artists.forEach(rawArtist => {

            const artist = new Artist(rawArtist)
            rawArtist._id = rawArtist.id
            album.artists.push(rawArtist.id)

            // Note that we will not add artists that already are in the Upsert Array
            // Also note that Artist object in Album has less info

            if (!user.artists.includes(rawArtist.id)) {    
                const upsertArtist = {
                    'updateOne': {
                      'filter': { '_id': rawArtist.id },
                      'update': artist,
                      'upsert': true
                    }
                };
                
                artistUpsertOperations.push(upsertArtist)
            }
        });

        // Add the album to the Bulk Upsert Array
        const upsertAlbum = {
            'updateOne': {
              'filter': { '_id': rawTrack.album.id },
              'update': album,
              'upsert': true
            }
        };

        albumUpsertOperations.push(upsertAlbum)

        // For each artist in the track, add it to the Bulk Upsert Array
        // Also link the push the artist id to the track's artist array
        rawTrack.artists.forEach(rawArtist => {

            const artist = new Artist(rawArtist)
            rawArtist._id = rawArtist.id
            track.artists.push(rawArtist.id)

            // Note that we will not add artists that already are in the Upsert Array
            // Also note that Artist object in Album has less info
            if (!user.artists.includes(rawArtist.id)) {
    
                const upsertArtist = {
                    'updateOne': {
                        'filter': { '_id': rawArtist.id },
                        'update': artist,
                        'upsert': true
                    }
                };

                artistUpsertOperations.push(upsertArtist)
            }
        });
        
        // Add the track to the Bulk Upsert Array
        const upsertTrack = {
            'updateOne': {
                'filter': { '_id': rawTrack.id },
                'update': track,
                'upsert': true
            }
        };
        
        trackUpsertOperation.push(upsertTrack)
    })

    try {
        await Promise.all([
            Artist.collection.bulkWrite(artistUpsertOperations), 
            Album.collection.bulkWrite(albumUpsertOperations), 
            Track.collection.bulkWrite(trackUpsertOperation)
        ]);
        var userResult = await User.findOneAndUpdate({'_id': user._id}, user, {upsert: true});
        return res.status(200).json(user)
    } catch (error) {
        return res.status(500).json(error)
    }
})

const getTest = router.get('/', async (req, res, next) => {
    var rez = await User.findById("sandelghimup").populate([{
        path: 'tracks',
        model: 'Track',
        populate: [{
           path: 'album',
           model: 'Album',
           populate: { 
               path: 'artists',
               model: 'Artist'
            }
        },
        {
            path: 'artists',
            model: 'Artist'   
        }],
    }, 
    {
        path: 'artists',
        model: 'Artist'
    }])
    return res.status(200).json(rez);
})

app.use(postTest)
app.use(getTest)

app.use((req, res, next) => {
    const error = new Error('Not Found')
    error.status = 404
    next(error)
});

app.use((error, req, res, next) => {
    res.status(error.status || 500)
    res.json({
        error: error.message
    })
});

server.listen(port, function() {
    console.log('Listening on ' + port)
});