# barebones

A bare-bones admin dashboard.

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

Copyright © 2018 FIXME
