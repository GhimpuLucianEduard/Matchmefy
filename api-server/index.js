const express = require('express')
const http = require('http')
const morgan = require('morgan')
const bodyParser = require('body-parser')
const router = express.Router()

const app = express()
const port = process.env.PORT || 8080
const server = http.createServer(app)

app.use(morgan('dev'))
app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())

var home = router.get('/', function (req, res, next) {
    return res.status(200).json("Hello!")
})

app.use(home);

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