# 학습 계획

# 소켓에 대해 학습하기

## 자바의 Socket 학습하기

저번 주에 공부를 못했다 다시 공부하자
Socket 이 정확히 무엇인지 모르고 있다. 잘 알아보자.

## 자바의 ServerSocket 에 대해서 학습하기

```text
try (ServerSocket listenSocket = new ServerSocket(port)) {
        Socket connection;
        while ((connection = listenSocket.accept()) != null) { // 학습해야하는 코드
        // 요청 응답 로직
        }
}
```
위의 코드의 `(connection = listenSocket.accept()) != null` 부분이 어떻게 동작하는지 궁금하다.
listenSocket.accept() 이 어떤 동작으로 connection 에 할당을 해주고, 다음 요청이 들어올때까지, connection 에 null 을 반환해주는지 궁금하다.


## 표준 HTTP 헤더에 대해 학습하기

표준을 공부하지 않으니, 이상한데서 실수를 많이 하는 것 같다.

## 자바 공부를 위한 Reflection 학습

Reflection 이 여러군데에서 많이 쓸모가 있을 것 같다.

## 호눅스의 AWS 강의 보기
