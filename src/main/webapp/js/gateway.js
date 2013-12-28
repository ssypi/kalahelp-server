var helpdesk = helpdesk || {};

(function(app, $) {
    "use strict";

    var Gateway = function () {
        var apiUrl = "http://syp.kapsi.fi/tc/klo/api/";
//        var apiUrl = "http://localhost:8080/kloSpring/api/";

        function parseApiResponse(response) {
        }

        function ajaxPostJson(url, data, callback) {
            callback = callback || function() {};
            $.ajax({
                type: "post",
                url: apiUrl + url,
                async: true,
                contentType: "application/json",
                accept: "application/json",
                data: JSON.stringify(data),
                success: function (returnData) {
                    console.log("message sent");
                    callback(true, returnData);
                },
                error: function (xhr) {
                    callback(false, xhr.status + ":" + xhr.statusText);
                }
            })
        }

        function ajaxGet(url, callback) {
            callback = callback || function () {};
            $.ajax({
                type: 'get',
                url: apiUrl + url,
                async: true,
                accept: "application/json",
                success: function (data) {
                    console.log("ajax get success.");
                    callback(data);
                },
                error: function (xhr) {
                    callback(false, xhr.status + ":" + xhr.statusText);
                }
            })
        }

        function getCategories(callback) {
            ajaxGet("category", callback);
        }

        function requestChat(username, callback) {
            var chatRequest = {};
            chatRequest.nickname = username;
            ajaxPostJson("chat/", chatRequest, callback);
        }

        function sendMessage(chatId, message, nickname, callback) {
            var chatMessage = {};
            chatMessage.content = message;
            chatMessage.writer = nickname;

            console.log("sending " + message + " to " + chatId);
            ajaxPostJson("chat/" + chatId, chatMessage, callback);
        }

        function saveTicket(ticket, callback) {
            console.log(ticket);
            console.log(JSON.stringify(ticket));
            ajaxPostJson("ticket/", ticket, callback);
        }

        return {
            getCategories : getCategories,
            saveTicket : saveTicket,
            sendMessage : sendMessage,
            requestChat : requestChat
        }
    };

    app.gateway = new Gateway();
})(helpdesk, jQuery);