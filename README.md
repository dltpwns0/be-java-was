# 학습 계획

# 루카스의 미션 구현 

공부를 전부 하고, 루카스 미션을 진행하면 진도가 많이 늦는거 같다.
그러니까 루카스의 미션을 구현하면서 틈틈히 아래의 공부 목록 공부하자.

# 소켓에 대해 학습하기

## 자바의 Socket 학습하기

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

# InputStream, BufferedReader 및 DataOutputStream  에 대해서 학습하기

InputStream 을 Buffer 에 저장해주는 BufferedReader 을 아무것도 알지 못한 상태로 사용을 했었다.
InputStream 이 정확히 무엇인지, 어떤 방식으로 동작하는지 공부할 필요성이 있을 것 같다.

# 스프링의 HTTP 헤더의 파싱 방법 공부

스프링의 코드가 코드의 정답이라고 생각한다.
그러니까 스프링은 어떤식으로 파싱을 하는지 알아보자

