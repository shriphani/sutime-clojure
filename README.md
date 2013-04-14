# sutime-clojure

A simple wrapper around the time functionality in Stanford's Core NLP Suite

## Usage


			(sutime-clojure.core/fetch-raw-date-strings 
    			"Apr 10 2012 and Apr 11 2012 was when I discovered that I was a mushroom.
            	I discovered I was a toadstool on 2013 January 10")
Returns:

    		["2012-04-10" "2012-04-11" "2013-01-10"]

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
