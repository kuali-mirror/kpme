/*
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
module.exports = function(grunt) {

	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),
		meta: {
			banner : '/*!\n' +
			' * <%= pkg.title %> v<%= pkg.version %> - <%= pkg.description %>\n' +
			' * Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author.name %> - <%= pkg.homepage %>\n' +
			' * License: <%= pkg.license %>\n' +
			' */\n\n'
		},
		uglify: {
			options : {
				banner : '<%= meta.banner %>',
				report: 'gzip'
			},
			dist: {
				files: {
					'jquery.timepicker.min.js': ['jquery.timepicker.js']
				}
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-uglify');

	grunt.registerTask('default', ['uglify']);

};
