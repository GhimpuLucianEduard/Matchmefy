const mongoose = require('mongoose')
const Artist = require('../models/Artist')

const albumSchema = mongoose.Schema({
    _id: String,
    album_type : String,
    artists : {
        type: String,
        ref: 'Artist'
    },
    available_markets : [String],
    external_urls: { 
        spotify: String
    },
    href : String,
    images: [{
        height : String,
        url : String,
        width : String
    }],
    name : String,
    release_date : String,
    release_date_precision : String,
    total_tracks : Number,
    type : String,
    uri : String
}, { versionKey: false })

module.exports = mongoose.model('Album', albumSchema)