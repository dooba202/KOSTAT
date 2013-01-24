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
         'highstock.export'
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
			this.el = options.el;
			
			var preparedData1 = [
	 			{
	 				name: "재고",				
	 				marker : {
	 					enabled : true,
	 					radius : 3
	 				},
	 				data: [ [ Date.UTC(2012,0), 100 ],[ Date.UTC(2012,1), 200 ],[ Date.UTC(2012,2), 250 ],[ Date.UTC(2012,3), 300 ],[ Date.UTC(2012,4), 350 ],[ Date.UTC(2012,5), 400 ],[ Date.UTC(2012,6), 250 ],[ Date.UTC(2012,7), 350 ],[ Date.UTC(2012,8), 400 ],[ Date.UTC(2012,9), 350 ] ]	
	 			}
			];
			
			this.chart = new Highcharts.StockChart({
				credits: false,
				
			    chart: {
			        renderTo: this.el
			    },
			    
			    title: {
		       		text: options.title
		    	},
		    	
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
		    	
			    yAxis: {
			    	/*
			    	labels: {
			    		formatter: function() {
			    			return (this.value > 0 ? '+' : '') + this.value + '%';
			    		}
			    	},
			    	*/
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
			    	selected: 0, 
			    	buttons: [
				    	/*
				    	{
							type: 'month',
							count: 3,
							text: '3m'
						},*/ {
							type: 'month',
							count: 6,
							text: '6m'
						}, {
							type: 'ytd',
							text: 'YTD'
						}, {
							type: 'year',
							count: 1,
							text: '1y'
						}, {
							type: 'all',
							text: 'All'
						}
					],
					inputDateFormat: '%Y-%m',
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
			    	xDateFormat: '%Y-%m',
			    	headerFormat: '<span style="font-size: 10px">{point.key}</span><br/>',
			    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>', //({point.change}%)
			    	//valueDecimals: 2
			    },
			    
			    series: preparedData1
			});
		},
		
		render : function(document) {
			//$(this.el).html( Mustache.to_html(template, document));
			return this;
		}
	});
});


