# sutime-clojure

A simple wrapper around the time functionality in Stanford's Core NLP Suite

## Usage


			(sutime-clojure.core/fetch-date-strings 
    			"Apr 10 2012 and Apr 11 2012 was when I discovered that I was a mushroom.
            	I discovered I was a toadstool on 2013 January 10")
Returns:

    		["2012-04-10" "2012-04-11" "2013-01-10"]


You can add a reference date using the clj-time library like so:

			(sutime-cljure.core/fetch-date-strings "Apr 10" (clj-time/now))

And it will use said reference date. Currently all the reference date stuff is handled by SUTime.
## License

Copyright © 2013 Shriphani Palakodety, CMU

Distributed under the MIT License.
