define(
	function() {
		if (!window.console) window.console = {}; //generate empty console object for IE8, IE9
		if (Function.prototype.bind && console && typeof console.log == "object") {
			[
			 "log", "info", "warn", "error", "assert", "dir", "clear", "profile", "profileEnd"
			].forEach(function (method) {
				console[method] = this.bind(console[method], console); //Function.prototype.call.bind(console[method], console);
			}, Function.prototype.call);
		}
		var logger = function(moduleName){
			logger.logDebug = true;
			logger.logLevel = 5; //default logging level is ALL
			logger.LOGLEVEL = {
					ALL: 5,
					ERROR: 1,
					WARN: 2,
					INFO: 3,
					LOG: 4
			};

			var loggerOnOff = function(boolean) {
				if (boolean == "on") {
					boolean = true;
				} else if (boolean == "off") {
					boolean = false;
				} 
				var booleanValue = !(!boolean);
				logger.logDebug = booleanValue;
			};
			
			var setLevel = function(level) {
				logger.logLevel = logger.LOGLEVEL[level]; 
			};
			
			var getLevel = function() {
				return logger.logLevel;
			};
			
			var addHeadder = function(args) {
				args[0] =  "[ " + moduleName + " ]: " + args[0];
				return args;
			};
			
			var log = function() {
				if (logger.logDebug && logger.logLevel > 3) {
					try {
						console.log.apply(console, addHeadder(arguments));
					} catch(e) {

					}
				};
			};
			var info = function() {
				if (logger.logDebug && logger.logLevel > 2) {
					try {
						console.info.apply(console, addHeadder(arguments));
					} catch(e) {

					}
				}
			};
			var warn = function() {
				if (logger.logDebug && logger.logLevel > 1) {
					try {
						console.warn.apply(console, addHeadder(arguments));
					} catch(e) {
						
					}
				}
			};
			var error = function() {
				if (logger.logDebug && logger.logLevel > 0) {
					try {
						console.error.apply(console, addHeadder(arguments));
					} catch(e) {

					}
				}
			};
			return {
				loggerOnOff: loggerOnOff, 
				setLevel: setLevel, 
				getLevel: getLevel, 
				log: log, 
				info: info, 
				warn: warn, 
				error: error
			};
		};
		return logger;
	}
);
