<div id="chat">
    <!--<div id="messagearea" class="chatArea">-->
    <textarea id="chat-area" readonly></textarea>
    <!--</div>-->

    <div id="chatId" hidden>${chatId}</div>
    <div id="nickname" hidden>${nickname}</div>
    <form action="#" onsubmit="return helpdesk.sendMessage();" method="post">
        <input id="message-box" placeholder="Write your message here"
               maxlength="50" type="text" autofocus required/>
        <input id="send-button" type="button" onclick="helpdesk.sendMessage()" value="Send"/>
    </form>
    <script src="js/chat.js"></script>
    <script src="js/gateway.js"></script>
</div>
