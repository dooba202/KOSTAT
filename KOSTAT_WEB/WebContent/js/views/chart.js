/*
 * 2013.1.18 
 * triggering events list (if silent mode is not true)
 */
var app = app || {};

define([ 'jquery', 
         'backbone', 
         'underscore', 
         'mustache', 
         //'text!templates/xmlResult.html!strip',
         'utils/local_logger',
         'highstock',
         'highstock.export',
         'highstock.themes.grid'
         ], 

function($,Backbone, _, Mustache, Logger) {
	var logger = new Logger("chartView");
		logger.setLevel("ALL");
		
	return Backbone.View.extend({
		/* < --common event handling */
		silent : false,
		setSilent: function(boolean) {
			var booleanValue = !(!boolean);
			this.silent = booleanValue;
		},
		
		eventTrigger: function() {
			if (arguments.length && !this.silent) {
				this.trigger.apply(this, arguments);
				return true;
			}
			return false;
		},
		/* common event handling --> */

		initialize : function(options) {
			logger.log("chartView init");
			//this.el = options.container;
			this.el = $("<div id="+ options.id + " style='" + "height:" + options.height + ";min-width:" + options.width + "'></div>");
		},
		
		render : function(data, unit) {
			Highcharts.setOptions({
				lang: {
	 				rangeSelectorZoom: "확대"
				}
			});
			
 			this.chart = new Highcharts.StockChart({
 				credits: false,
 				
 			    chart: {
 			        renderTo: this.el.attr("id")
 			    },
 			    /*
 			    title: {
 		       		text: title || '제목없음'
 		    	},
 		    	*/
 		    	
 		    	xAxis: {
 		    		dateTimeLabelFormats: 
 					{ 
 						second: '%Y년%m월%d일<br/>%H:%M:%S',
 						minute: '%Y년%m월%d일<br/>%H:%M',
 						hour: '%Y년%m월%d일<br/>%H:%M',
 						day: '%Y년<br/>%m월%d일',
 						week: '%Y년<br/>%m월%d일',
 						month: '%Y년%m월',
 						year: '%Y년'
 					},
 					minTickInterval: 24 * 3600 * 1000 * 30, //To keep one month interval at least
 					minRange: 3,
 		    	},
 		    	
 			    yAxis: {
 			    	labels : {
 				    		formatter: function() {
 				    			if (unit == '%') {
 				    				return (this.value > 0 ? '+' : '') + this.value + '%';
 				    			} else {
 				    				return this.value + unit;
 				    			}
 				    		}
 				    },
 			    	plotLines: [{
 			    		value: 0,
 			    		width: 2,
 			    		color: 'silver'
 			    	}]
 			    },
 				
 				navigator: {
 					enabled: true,
 					xAxis: {
 			    		dateTimeLabelFormats: 
 						{ 
 							second: '%Y-%m-%d<br/>%H:%M:%S',
 							minute: '%Y-%m-%d<br/>%H:%M',
 							hour: '%Y-%m-%d<br/>%H:%M',
 							day: '%Y<br/>%m-%d',
 							week: '%Y<br/>%m-%d',
 							month: '%Y-%m',
 							year: '%Y'
 						}
 		    	},
 					
 				},
 				
 			    legend: {
 			    	enabled: true,
 			    	align: 'right',
 		        	backgroundColor: '#FCFFC5',
 		        	borderColor: 'black',
 		        	borderWidth: 2,
 			    	layout: 'vertical',
 			    	verticalAlign: 'top',
 			    	y: 100,
 			    	shadow: true
 			    },	
 			    
 			    rangeSelector: { 
 			    	selected: 2, 
 			    	buttons: [
 				    	{
 							type: 'month',
 							count: 3,
 							text: '3m'
 						}, 
 						{
 							type: 'month',
 							count: 6,
 							text: '6m'
 						}, 
 						/*
 						{
 							type: 'ytd',
 							text: 'YTD'
 						},*/ 
 						{
 							type: 'year',
 							count: 1,
 							text: '1y'
 						}, {
 							type: 'all',
 							text: 'All'
 						}
 					],
 					inputDateFormat: '%Y년%m월',
 					inputEditDateFormat: '%Y-%m'
 			    },
 			    /*
 			    plotOptions: {
 			    	series: {
 			    		compare: 'percent'
 			    	}
 			    },
 				*/			    
 			    tooltip: {
 			    	xDateFormat: '%Y년%m월',
 			    	headerFormat: '<span style="font-size: 10px">{point.key}</span><br/>',
 			    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}' + unit +'</b><br/>', //({point.change}%)
 			    	//valueDecimals: 2
 			    },
 			    
 			    series: data
 			});
			return this;
		}
	});
});


