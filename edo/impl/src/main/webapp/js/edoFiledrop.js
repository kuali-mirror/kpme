$(function() {
    var dropbox = $('#dropzone'),
        message = $('.message', dropbox),
        filelist = $('#filelist');
    var filearray = new Array();
    var isError = false;

    dropbox.filedrop({
        paramname: 'uploadFile',
        maxfiles: 5,
        maxfilesize: 4000,
        url: uploadURL,
        data: { "checklistItemID": checklistItemID,
                "nidFwd": nidFwd },

        uploadFinished: function(i, file, response ) {
            $("#dropzone").css('background', '#ccc');
            filearray.push(file.name);
        },
        dragOver: function() {
            $("#dropzone").css('background', '#eee');
        },
        dragLeave: function() {
            $("#dropzone").css('background', '#ccc');
        },
        afterAll: function() {
            location.reload();
        },
        error: function( err, file ) {
            switch(err) {
                case 'BrowserNotSupported':
                    showMessage('Your browser does not support HTML5 file uploads');
                    break;
                case 'TooManyFiles':
                    alert( 'Too many files!  Please select no more than 5 files at a time.')
                    break;
                case 'FileTooLarge':
                    alert( file.name + 'is too large!  Please upload files up to 1000Mb only.')
                    break;
                case 'FileTypeNotAllowed':
                    alert( 'This file type is not allowed.')
                    break;
                case 'NotFound':
                    alert( 'The file was not found.');
                    isError = true;
                    break;
                case 'NotReadable':
                    alert( 'The file is not readable by the browser.')
                    break;
                case 'AbortError':
                    alert( 'The file transfer was aborted.')
                    break;
                case 'ReadError':
                    alert( 'There was an error reading the file.')
                    break;
                default:
                    break;
            }
        },
        beforeEach: function( file ) {
            // implement file name pattern match here
            // this will allow file type filtering on upload
        },

        uploadStarted: function( i, file, len) {
            createFileEntry(file);
        },

        progressUpdated: function(i, file, progress) {
            // $.data(file).find('.progress').width(progress);
        }

    });

    var template = '<div class="progressHolder"><div class="progress"></div></div>';

    function createFileEntry(file) {
        var preview = $(template);
        var reader = new FileReader();

        reader.onload = function(e) {
            // e.target.result will hold the DataURL
        }

        if (isError) {
            reader.abort();
            return false;
        }

        reader.readAsDataURL(file);
        //message.hide();
        preview.appendTo($("#filelist"));
        $("#uploadresult").css("display", "block");
        $.data(file,preview);
    }

    function showMessage(msg) {
        message.html(msg);
    }
})


