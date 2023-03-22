# API
## 1. 블로그 검색
### 1.1. 기본 정보
```
GET /blog/search
host: localhost:8080
```

### 1.2. 요청 데이터
| NAME  | TYPE    | DESCRIPTION                                               | REQUIRED |   
|:------|:--------|:----------------------------------------------------------|:---------|
| query | String  | 검색을 원하는 질의어                                               | O        |
| sort  | String  | 결과 문서 정렬 방식 accuracy(정확도 순) or recency(최신순), 기본값 accuracy | X        |
| page  | Integer | 결과 페이지 번호, 1 ~ 50 사이의 값, 기본 값 1                           | X        |
| size  | Integer | 한 페이지에 보여질 문서 수, 1 ~ 50 사이의 값, 기본 값 10                    | X        |
| type  | String  | 블로그 검색을 요청할 서비스 KAKAO or NAVER, 기본값 KAKAO                 | X        |

### 1.3. 응답 데이터
#### meta
| NAME           | TYPE    | DESCRIPTION          |   
|:---------------|:--------|:---------------------|
| total_count    | Integer | 검색된 문서 수             |
| pageable_count | Integer | 검색된 문서 중 노출 가능 문서 수  |
| is_end         | Boolean | 현재 페이지가 마지막 페이지인지 여부 |

#### documents
| NAME           | TYPE             | DESCRIPTION          |   
|:---------------|:-----------------|:---------------------|
| title| 	String| 	블로그 글 제목            |
|contents	|String	    |블로그 글 요약
|url	    |String	    |블로그 글 URL
|blogname	|String	    |블로그의 이름
|thumbnail	|String	    |검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
|datetime	|Datetime	|블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]

### 1.4. Sample
#### 1.4.1. 요청
```
curl -v -X GET "http://localhost:8080/blog/search?query=de&sort=recency&page=2&size=5"
```
#### 1.4.2. 응답
```
{
  "meta": {
    "pageable_count": 800,
    "total_count": 2456224,
    "is_end": false
  },
  "documents": [
    {
      "blogname": "유용한 생활정보",
      "contents": "조미료를 넘어서는 오랜 요리 영향력을 가지고 있습니다. 2022년에는 프랑스 요리의 영광에 전념하는 16에이커 규모의 거대한 부지인 Cité Internationale <b>de</b> la Gastronomie et du Vin을 시작했습니다. 2016년 판텔레리아(Pantelleria) 에 이탈리아 의 최신 국립공원이 문을 열었습니다 . 이 조용한 섬은 일년 내내...",
      "datetime": "2023-03-22T22:14:24.000+09:00",
      "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/aV2qem5GS1",
      "title": "세계 최고의 명소. TIME 선정",
      "url": "http://mocha-mom.com/101"
    },
    {
      "blogname": "minx",
      "contents": "first to reduce documents to those where the array contains the match { &#34;$match&#34;: { &#34;authors&#34;: { &#34;$regex&#34;: &#34;Alex&#34;, &#34;$options&#34;: i } }}, // Unwind to &#34;<b>de</b>-normalize&#34; the document per array element { &#34;$unwind&#34;: &#34;$authors&#34; }, // Now filter those document for the elements that match { &#34;$match...",
      "datetime": "2023-03-22T22:08:18.000+09:00",
      "thumbnail": "",
      "title": "mongoose find를 사용하여 문자열의 일부를 포함하는 모든 값을 가져오려면 어떻게 해야 합니까?",
      "url": "http://minx.tistory.com/168"
    },
    {
      "blogname": "비비다이어리♬",
      "contents": "둘째날 바로 스위스로 이동할거기 때문에 첫 숙소는 무조건 이동하기 편한곳으로 잡았다 ​ Hôtel Mercure Lyon Centre Château Perrache Esplanade <b>De</b> La Gare, 12 Cr <b>de</b> Verdun Rambaud, 69002 Lyon, 프랑스 ​ 리옹역 도보 1분거리에 위치해 있는 호텔 진짜 호텔 나와서 왼쪽으로 가면 리옹역이다 ​ ​ 우리는 다음날 떼제베...",
      "datetime": "2023-03-22T22:00:00.000+09:00",
      "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/HoLJBxNFXr",
      "title": "2023년 신혼여행기 - BB&#39;s in Paris(파리호텔추천 : 머큐어 가르드 리옹 TGV) 스위스 가기 최적의 숙소",
      "url": "https://blog.naver.com/holl7777/223052751820"
    },
    {
      "blogname": "mbctv",
      "contents": "init&#39;, &#39;add_directory_rewrite&#39; ); ?&gt; 이것은 언어를 취득하는 데까지 유효하지만, 현재 직면하고 있는 문제는 _permalink()에 /en/ 또는 /fr/ 또는 /<b>de</b>/ 또는 그 어느 언어에 대해서도 &#34;/%lang%/&#34;가 있다는 것입니다.자세한 내용을 추가하기 위해 나의 permalink structure는 /%pargate%/%category%/%postname...",
      "datetime": "2023-03-22T22:00:00.000+09:00",
      "thumbnail": "",
      "title": "워드프레스 add_rewrite_tag(), add_rewrite_rule() 및 post_link()",
      "url": "http://mbctv.tistory.com/154"
    },
    {
      "blogname": "anycallme",
      "contents": "기능하는지를 설명하기 위해 매우 간단한 예를 만들었습니다. phoneformat.http://http://www.phoneformat.com/), 는, 유저의 로케일(en-US, ja-JP, fr-FR, <b>de</b>-<b>DE</b> 등)에 근거해 전화 번호를 포맷 할 수 있을 뿐만 아니라, 전화 번호의 유효성도 확인할 수 있습니다.구글 libphonenumber 프로젝트에 기반한 매우 견고한...",
      "datetime": "2023-03-22T21:57:27.000+09:00",
      "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/Hl11ttH1y8F",
      "title": "전화 번호와 신용카드 번호를 Angular로 포맷합니다.JS",
      "url": "http://anycallme.tistory.com/86"
    }
  ],
  "requestInfo": {
    "query": "de",
    "sort": "recency",
    "page": 2,
    "size": 5,
    "type": "KAKAO"
  }
}
```


## 2. 블로그 검색 키워드 랭킹
### 2.1. 기본 정보
```
GET /blog/search/keyword/rank
host: localhost:8080
```

### 2.2. 요청 데이터
| NAME  | TYPE    | DESCRIPTION                                               | REQUIRED |   
|:------|:--------|:----------------------------------------------------------|:---------|

### 2.3. 응답 데이터
#### list -> 검색량이 많은 키워드부터 순서대로
| NAME    | TYPE    | DESCRIPTION   |   
|:--------|:--------|:--------------|
| id      | Integer | 키워드 id 값      |
| keyword | String  | 검색된 키워드 명     |
| count   | Integer | 해당 키워드가 검색된 수 |


### 2.4. Sample
#### 2.4.1. 요청
```
curl -v -X GET "http://localhost:8080/blog/search/keyword/rank"
```
#### 2.4.2. 응답
```
[
  {
    "id": 1,
    "keyword": "카카오 뱅크",
    "count": 10
  },
  {
    "id": 2,
    "keyword": "카카오 페이",
    "count": 9
  },
  {
    "id": 3,
    "keyword": "카카오 톡",
    "count": 8
  },
  {
    "id": 4,
    "keyword": "카카오 쇼핑",
    "count": 7
  },
  {
    "id": 5,
    "keyword": "카카오 패션",
    "count": 6
  }
]
```

## 3. jar 다운로드 링크
https://drive.google.com/file/d/1-FaZafo3EZegJruHeS-K0fobYFENZM8S/view?usp=sharing