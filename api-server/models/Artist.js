const mongoose = require('mongoose')

const artistSchema = mongoose.Schema({
    _id: String,
    external_urls: { 
        spotify: String
    },
    followers: {
        href : String,
        total : Number
    },
    genres: [String],
    href: String,
    images: [{
        height : String,
        url : String,
        width : String
    }],
    name : String,
    popularity : Int,
    type : String,
    uri : String
}, { versionKey: false })

module.exports = mongoose.model('Artist', artistSchema)