// HTML 요소들 참조
var memberNamePage = document.querySelector('#memberName-page');
var chatPage = document.querySelector('#chat-page');
var memberNameForm = document.querySelector('#memberNameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

// WebSocket 클라이언트와 사용자 이름 초기화
var stompClient = null;
var memberName = null;

// 색상 배열
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// 사용자 이름을 입력 받고 WebSocket 연결을 설정하는 함수
function connect(event) {
    memberName = document.querySelector('#name').value.trim();

    if (memberName) {
        memberNamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

// 연결이 성공했을 때 호출되는 함수
function onConnected() {
    // 채팅방에 구독
    stompClient.subscribe('/sub/chat/room/1', onMessageReceived);

    // 서버에 사용자 이름과 JOIN 메시지 전송
    stompClient.send("/pub/chat/enter",
        {},
        JSON.stringify({sender: memberName, content: 'JOIN'})
    );

    connectingElement.classList.add('hidden');
}

// 연결 실패 시 호출되는 함수
function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

// 메시지 전송 함수
function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: memberName,
            content: messageInput.value
        };
        stompClient.send("/pub/chat/message", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

// 메시지 수신 시 호출되는 함수
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var messageElement = document.createElement('li');

    if (message.content === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.content === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

// 메시지 발신자에 따라 색상을 결정하는 함수
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

// 이벤트 리스너 추가
memberNameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
