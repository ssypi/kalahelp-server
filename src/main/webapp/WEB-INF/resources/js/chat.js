var helpdesk = helpdesk || {};

(function (app) {
    "use strict";

    var chatArea = document.getElementById('chat-area');
    var chatId = document.getElementById('chatId').innerHTML;
    var nickname = document.getElementById('nickname').innerHTML;
    var asd = false;

    app.sendMessage = function () {
        if (!asd) {
            app.getMessages();
            asd = true;
        }
        var messageBox = document.getElementById('message-box');
        app.gateway.sendMessage(chatId, messageBox.value, nickname);
        chatArea.value = chatArea.value + "\n" + nickname + ": " + messageBox.value;
        messageBox.value = "";
        return false;
    };

    app.getMessages = function() {
        app.gateway.getMessages(chatId, refreshChatArea);
        setTimeout(app.getMessages, 2000);
    };

    function refreshChatArea(data) {
        var messages = data.result;
        var i;
        chatArea.value = "";
        for(i = 0; i < messages.length; i++) {
            chatArea.value = chatArea.value + "\n" + messages[i].writer + ": " + messages[i].content;
        }
    }
})(helpdesk);