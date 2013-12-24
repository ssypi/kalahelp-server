<div id="chat">
    <!--<div id="messagearea" class="chatArea">-->
    <textarea id="chat-area" readonly></textarea>
    <!--</div>-->

    <form action="" data-bind="submit: sendMessage">
        <input id="message-box" placeholder="Write your message here"
               maxlength="50" type="text" data-bind="value: message" autofocus required/>
        <input id="send-button" type="submit" value="Send"/>
    </form>
    <button data-bind="click: requestChat">Chat</button>
</div>
