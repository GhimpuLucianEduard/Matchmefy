const mongoose = require('mongoose')
const Artist = require('../models/Artist')
const Album = require('../models/Album')

const trackSchema = mongoose.Schema({
    _id: String,
    album : {
        type: String,
        ref: 'Album'
    },
    artists : {
        type: [String],
        ref: Artist
    },
    available_markets : [String],
    disc_number : Number,
    duration_ms : Number,
    explicit : Boolean,
    external_ids: { 
        isrc: String
    },
    external_urls: { 
        spotify: String
    },
    href : String,
    is_local : Boolean,
    name : String,
    popularity : Int,
    preview_url : String,
    track_number : Int,
    type : String,
    uri : String
}, { versionKey: false })

module.exports = mongoose.model('Track', trackSchema)