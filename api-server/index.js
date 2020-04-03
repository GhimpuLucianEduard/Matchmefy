const dotenv = require('dotenv')
const express = require('express')
const http = require('http')
const morgan = require('morgan')
const bodyParser = require('body-parser')
const router = express.Router()
const mongoose = require('mongoose')
const User = require('./models/User.js')
const Artist = require('./models/Artist.js')

const app = express()
const port = process.env.PORT || 8080
const server = http.createServer(app)

dotenv.config({ debug: process.env.DEBUG })
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

    // const newUser = new User(req.body.user);
    // newUser._id = req.body.id

    // try {
    //     await newUser.save()
    // } catch (e) {
    //     console.log(e)
    // }

    var bulkOps = [];
    req.body.artists.forEach(element => {
        const artist = new Artist(element)
        artist._id = element.id

        let upsertDoc = {
            'updateOne': {
              'filter': { '_id': element.id },
              'update': artist,
              'upsert': true
            }};
          bulkOps.push(upsertDoc);
    });

    await Artist.collection.bulkWrite(bulkOps)


    return res.status(200).json("Ok")
})

app.use(postTest)

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