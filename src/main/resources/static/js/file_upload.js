$('#studentImage').bind('change', function () {
    var filename = $("#studentImage").val();
    if (/^\s*$/.test(filename)) {
        $(".image-file-upload").removeClass('active');
        $("#studentImageChosen").text("No file chosen...");
    } else {
        $(".image-file-upload").addClass('active');
        $("#studentImageChosen").text(filename.replace("C:\\fakepath\\", ""));
    }
});

