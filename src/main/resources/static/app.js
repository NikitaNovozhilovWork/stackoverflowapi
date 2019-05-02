function search() {
    $("#search_results").html("");
    $("#load").attr('data-next-page', 1);
    load();
}

function load() {
    var request = {
        title: $("#searchTitle").val(),
        page: Number($("#load").attr('data-next-page')),
        size: 10,
        order: "desc",
        sort: "activity",
        site: "stackoverflow"
    };
    if (!isNaN(request.page) && request.page !== 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/v1/search",
            dataType: 'json',
            data: JSON.stringify(request),
            success: function (searchResponse) {
            if (searchResponse.hasMore) {
                $("#load").prop("disabled", false);
            } else {
                $("#load").prop("disabled", true);
            }
            $("#load").attr('data-next-page', Number($("#load").attr('data-next-page')) + 1);
            var questions = searchResponse.questions;
            for (var i = 0 ; i < questions.length ; i++) {
                showSearchResult(questions[i]);
            }
            },
            error: function (e) {
                console.log('loading error: ' + e);
            }
        });
    }
}

function showSearchResult(message) {
    var isAnsweredColor = message.answered === true && "class=\"bg-success\"" || "class=\"bg-danger\"";
    $("#search_results").append("<tr id=" + message.id + ">" +
            "<td>" + message.ownerName + "</td>" +
            "<td>" + message.title + "</td>" +
            "<td>" + new Date(message.creationDate * 1000).toUTCString() + "</td>" +
            "<td " + isAnsweredColor + ">" + message.answered + "</td>" +
            "<td>" + "<a href=\"" + message.link + "\">Original question</a></td></tr>");
}

$(function () {
    $("form").submit(function( event ) {
        event.preventDefault();
        search();
    });
    $("#load").click(function() { load(); });
});