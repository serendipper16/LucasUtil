# 설명
- 자주 사용하는 기능을 라이브러리화 하였습니다.
- 호출 가능 클래스
    - BasicUtil(분류하지 않은 기능)
    - KeyHandleUtil(뒤로가기 두번 눌러서 종료)
    - LogUtil(디버그모드에서만 로그를 출력하고 호출클래스 및 메소드명까지 출력)
    - VersionUtil(앱 버전확인 및 비교)

# 사용방법

## BasicUtil

```java
    String tel = BasicUtil.getLine1Number(this);    // 전화번호 가져오기
    float px = BasicUtil.dpToPx(this, 50);  // dp를 px로 변환
    BasicUtil.sendCall(this, "01012345678", false); // 전화걸기(Direct Call = false)
    BasicUtil.launchWebsite(this, "https://github.com");    // 웹사이트 열기
    ...
```

## KeyHandleUtil

```java
    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        KeyHandleUtil.doubleBackFinish(this);   // 뒤로가기 두번눌러서 종료
    }
```

## LogUtil

```java
    LogUtil.init(this, null);

    LogUtil.i("정보출력");
    LogUtil.e("에러출력");
```

## VersionUtil

```java
        int versionCode = VersionUtil.getVersionCode(this);
        String versionName = VersionUtil.getVersionName(this);

        int ret = VersionUtil.checkVersionName(this, "1.0.0");    // 현재앱버전과 서버버전을 비교

        switch (ret){
            // 메이저 버전이 낮을경우
            case VersionUtil.VERSION_LOW_MAJOR:
                break;

            // 마이너 버전이 낮을 경우
            case VersionUtil.VERSION_LOW_MINOR:
                break;

            // 패치 버전이 낮을 경우
            case VersionUtil.VERSION_LOW_PATCH:
                // TODO:
                break;

            // 최신버전일 경우
            case VersionUtil.VERSION_LATEST:
                // TODO:
                break;

            // 버전체크 오류
            case VersionUtil.VERSION_ERROR:
                // TODO:
                break;
        }
```

# 설치


## Maven
```xml
<dependency>
  <groupId>com.lucaskim</groupId>
  <artifactId>lucasutil</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

## Gradle
```xml
compile 'com.lucaskim:lucasutil:1.0.0'
```

## Ivy
```xml
<dependency org='com.lucaskim' name='lucasutil' rev='1.0.0'>
  <artifact name='lucasutil' ext='pom' ></artifact>
</dependency>
```

# 오픈소스 라이센스
```xml
Copyright 2017 Lucas Kim

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
