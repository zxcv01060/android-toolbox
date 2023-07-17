# 工具箱(Android)

## 開發環境及使用技術
---

* 電腦：Macbook M1 Pro
* IDE：[Android Studio](https://developer.android.com/studio)
* 程式語言：[Kotlin](https://kotlinlang.org/) 1.8.22(JDK 17)
* 主要套件：[Jetpack Compose](https://developer.android.com/jetpack/compose?hl=zh-tw)
* 樣式套件：[Material 3](https://m3.material.io/)

## 注意事項

1. 目前是自己用的，畫面完全沒有經過設計
2. 此App的最低版本為Android Pie(28)

## 螢幕截圖
---

## 功能
---

### ✅日期

以下皆可設定是否包含開始和結束當天

1. 能夠計算某一天的三天後是幾號、星期幾
2. 能夠計算兩天一共相隔幾天

### ✅短網址還原

目前已支援的短網址如下：

1. https://reurl.cc

### ❌發票

1. 能夠查詢某一期的發票的中獎號碼清單
2. 能夠輸入一個發票號碼，顯示是否中獎
3. 能夠開啟相機掃描電子發票

### ❌PDF

1. 能夠將一個PDF檔案拆成每頁一個PDF檔案
2. 能夠將一個PDF檔案內的所有圖片讀出

### ⚠️產生器

1. ✅能夠隨機產生一組UUID
2. ❌能夠輸入資料庫連線資訊，而後選擇要產生Java、C#、[DB Mate(Go)](https://github.com/amacneil/dbmate)
   的基本連線字串
3. ❌能夠產生C# Entity Framework的反向工程指令(Db Scaffold)

### ⚠️雜湊、編碼

1. Base64
2. 能做MD5加密
3. 能做SHA系列加密
4. 能夠解析JWT Token
   ⚠️還沒做到，其他可行

### ❌資料表實體

### ❌QR Code

1. 能夠掃描QR Code
2. 能夠產生QR Code
