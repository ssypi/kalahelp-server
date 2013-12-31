<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="chooser">
    <a href="#" class="choose-button" onclick="return showChatOptions()">Chat</button>
    <a href="ticket/new/" class="choose-button">Ticket</a>
    <div id="message">
        <c:out value="${message}"/>
    </div>
    <br>
    <br>
    <div id="chat-options" hidden>
        <form class="pure-form pure-form-stacked" action="chat/new/">
            <label for="chat-options">Nickname:</label>
            <input id="nickname" name="nickname" placeholder="Please enter your nickname" type="text"/>
            <label for="category">Problem Type:</label>
            <select id="category" name="category">
                <option>Other</option>
                <option>Billing</option>
            </select>
            <button class="pure-button" type="submit">Ok</button>
        </form>
    </div>
</div>

<script>
    function showChatOptions() {
        document.getElementById('chat-options').style.display = 'block';
        return false;
    }
</script>
