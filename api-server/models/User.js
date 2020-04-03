const mongoose = require('mongoose')

const userSchema = mongoose.Schema({
    _id: String,
    display_name: String,
    email: String,
    external_urls: { 
        spotify: String
    },
    followers: {
        href : String,
        total : Number
    },
    href: String,
    images: [{
        height : String,
        url : String,
        width : String
    }],
    type: String,
    uri: String
    
}, { versionKey: false })

module.exports = mongoose.model('User', userSchema)