# barebones

A bare-bones admin dashboard.

I just put whatever I wanted to try to implement and so far these include:

* Integration with Chart.js and Leaflet
* GitHub style calendar heatmap

## Screenshots

![alt text](https://raw.githubusercontent.com/burhanloey/barebones/master/screenshots/calendar_heatmap.png "GitHub style calendar heatmap")

## Credits

* [color_ramps](https://github.com/madams1/color_ramps) for calendar heatmap

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start web server for development (using GNU Screen), run:

    screen -c screen
    
## Building

To build the project for deployment, run:

    ./make.sh

To deploy:

    java -jar target/barebones-<version>-standalone.jar

## License

MIT License

Copyright © 2018 Burhanuddin Baharuddin
