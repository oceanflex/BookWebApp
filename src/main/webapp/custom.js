$('#insertModal').on('shown.bs.modal', function () {
    $('#myInput').focus();
});
$('#updateModal').on('shown.bs.modal', function (info) {
    var author_id = $(info.relatedTarget).data('aid');
    var author_name = $(info.relatedTarget).data('aname');
    var date_added = $(info.relatedTarget).data('adate');
    $("#uId").attr('value', author_id);
    $("#uName").attr('value', author_name);
    $("#uDate").attr('value', date_added);
    $('#myInput').focus();
});