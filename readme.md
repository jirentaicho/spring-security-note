# 注意すべき箇所

以下はセッション固定化攻撃の対象になる可能性がある

* セッション情報をURLパラメータに含める
* セッションIDをログイン後に新しく発行しない

```java
http.sessionManagement().enableSessionUrlRewriting(true);
http.sessionManagement().sessionFixation().none();
http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
```


以下はXSS攻撃の対象になる可能性がある

* 入力された文字列をエスケープせずに表示
  * アプリケーション側でバリデーションなどの入力チェックを行うのも重要


```html
<P th:utext="${input}"></P>
```



#　メモ


#### 依存

```
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
```

#### SpringSecurityの設定クラス

* SecurityConfiguration.java

ログインページに関する設定をする

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin( form -> {
                form.loginPage("/login");
                form.loginProcessingUrl("/login");
                form.usernameParameter("username");
                form.passwordParameter("password");
                form.defaultSuccessUrl("/", true);
                form.failureUrl("/login?error");
    });
    return http.build();
}
```

コントローラーでルーティングを設定しておく

```java
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
```

#### ログインページ

login.html

templatesに配置

パラメータ

* username
* password

アクション先

/login POST


