# 初次開啟專案的事前準備

- Java(如果您已經擁有JDK 11的任意版本，可略過此步驟)
    1. 安裝OpenJDK，版本：AdoptOpenJDK 11.0.8+10(JVM：HotSpot)
        - Windows
            - x86：[msi](https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x86-32_windows_hotspot_11.0.8_10.msi)、[zip](https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x86-32_windows_hotspot_11.0.8_10.zip)
            - x64：[msi](https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x64_windows_hotspot_11.0.8_10.msi)、[zip](https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x64_windows_hotspot_11.0.8_10.zip)
        - [Linux](https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x64_linux_hotspot_11.0.8_10.tar.gz)
        - macOS：[pkg](https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x64_mac_hotspot_11.0.8_10.pkg)、[tar.gz](https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x64_mac_hotspot_11.0.8_10.tar.gz)
    2. 請至專案根目錄(與`build.gradle`同層)下自行建立`gradle.properties`檔案，檔案內容如下

        ```
        org.gradle.java.home={你的JDK 11目錄，如果你看到我，就表示你沒有改成自己的}
        ```

- 資料庫
    1. [下載MySQL資料庫](https://downloads.mysql.com/archives/installer/)(Windows) (如果您已經擁有MySQL 8的任意版本Server，可略過此步驟)
    2. 下載專案資料庫備份檔(目前為1.2.6版)

        [camping(structure)-1.2.6.sql](./README-media/camping(structure)-1.2.6.sql)

    3. 啟動`MySQL Workbench`，選擇左上方`Server → Data Import`

        ![./README-media/Untitled.png](./README-media/0.png)

    4. 在`Import Options`區塊中選擇第二個選項(`Radio Button`)，並點選其最後面的按鈕，選擇第二點下載的`.sql`檔
    5. 點選右下方的`Start Import`按鈕即可完成備份(注：若無找到`Start Import`，需要調高螢幕解析度，1920x1080最佳)
- 環境變數
    - Windows
        1. 開啟檔案總管：對本機點右鍵，選擇最後的"內容"

            ![./README-media/1.png](./README-media/1.png)

        2. 選擇左上方的"進階系統設定"

            ![./README-media/2.png](./README-media/2.png)

        3. 選擇"環境變數"
        4. 在"系統變數"區塊選擇"新增"(即由上往下數的第二個"新增"按鈕，或者說"確定"按鈕的左上方那顆"新增"按鈕)

            ![./README-media/3.png](./README-media/3.png)

        5. 在變數名稱區塊輸入"`log.path`"，變數值區塊輸入伺服器運行日誌的存放資料夾，我會自動在該資料夾下建立名為"`camping`"(專案名稱)的資料夾，可以用相對(以專案根目錄來說)或絕對路徑來設定

            ![./README-media/4.png](./README-media/4.png)

        6. 設定完成即可依序點選"確定"按鈕關閉視窗
        7. 若此時您的IDE或CMD是啟動狀態，請重新啟動IDE或CMD(需關閉所有IDE或CMD視窗後再重新啟動)
    - Linux & Mac

        我沒有用Linux或Mac，所以請自行上網尋找設定方式

        1. 變數名稱：`log.path`
        2. 變數值請自行設定，變數值的意義為設定伺服器運行日誌的存放資料夾，我會自動在該資料夾下建立名為"`camping`"(專案名稱)的資料夾，可以用相對(以專案根目錄來說)或絕對路徑來設定
- IDE
    - Jetbrains IntelliJ IDEA (截圖版本為2020.2.2，且介面有套用`Material Theme`)
        - 開啟專案
            - 歡迎頁面
                1. 選擇`Open or Import`

                    ![./README-media/5.png](./README-media/5.png)

                2. 選擇專案根目錄後，選擇`Ok`即可
            - 已開啟其他專案
                1. 選擇左上方的File，在下拉選單中選擇`Open...`

                    ![./README-media/6.png](./README-media/6.png)

                2. 選擇專案根目錄後，選擇`Ok`即可
        - JDK
            1. 請按鍵盤上的`Ctrl + Alt + Shift + S`開啟專案設定視窗

                ![./README-media/7.png](./README-media/7.png)

            2. 在`Project SDK:`處的下拉選單中選擇11.X.X版的JDK

                ![./README-media/8.png](./README-media/8.png)

            3. 若找不到11.X.X版的JDK，請點選空白處關閉下拉選單，並選擇下拉選單旁的`EDIT`按鈕

                ![./README-media/9.png](./README-media/9.png)

            4. 點選左上方的"+"號

                ![./README-media/10.png](./README-media/10.png)

            5. 選擇`Add JDK...`

                ![./README-media/11.png](./README-media/11.png)

            6. 選擇JDK根目錄後點選`OK`按鈕關閉選擇
            7. 點選左側導覽列的`Project`並在`Project SDK`的下拉選單中選擇剛剛新加入的JDK即可點選右下方的`OK`按鈕關閉視窗即可
        - Lombok
            - 歡迎頁面
                1. 選擇右下方的`Configure`
                2. 點選第二項：`Plugins`

                    ![./README-media/12.png](./README-media/12.png)

                3. 中間上方選擇`Marketplace`

                    ![./README-media/13.png](./README-media/13.png)

                4. 在搜尋欄中輸入：`Lombok`

                    ![./README-media/14.png](./README-media/14.png)

                5. 下載第一個插件即可
            - 已開啟其他專案
                1. 請按鍵盤上的`Ctrl + Alt + S`開啟IDE設定視窗
                2. 點選視窗左方列表的`Plugins`

                    ![./README-media/15.png](./README-media/15.png)

                3. 在上圖右上方中選擇`Marketplace`
                4. 在上圖中間上方的搜尋欄中輸入：`Lombok`

                    ![./README-media/16.png](./README-media/16.png)

                5. 下載上圖中的第一個插件，下載完成後重啟IDE
                6. 重啟完畢後，右下方會跳出一個視窗詢問是否啟用`Lombok`設定，展開後選擇`Enable`即可

                    ![./README-media/17.png](./README-media/17.png)

                    ![./README-media/18.png](./README-media/18.png)

    - Eclipse(截圖版本為2020.2)
        - 插件安裝
            1. 選擇選單列最右方的`Help → Eclipse Marketplace...`
            2. 搜尋`Gradle`並下載`Gradle IDE Pack`

                ![./README-media/19.png](./README-media/19.png)

            3. 點選下載後會在背景下載(右下角會有讀條)，安裝完畢後重複第一步，改成搜尋Spring STS並下載`Spring Tools 4 (aka Spring Tool Suite)`

                ![./README-media/20.png](./README-media/20.png)

            - 若找不到以上兩個或其中一個
                1. 請打開瀏覽器搜尋`Eclipse + 插件名稱`，例如：Eclipse Gradle IDE Pack

                    ![./README-media/21.png](./README-media/21.png)

                2. 點進標題有`Eclipse Marketplace`的網站
                3. 點擊`Install`按鈕下方一排`icon`的最右邊那個按鈕

                    ![./README-media/22.png](./README-media/22.png)

                4. 請確認`Eclipse`版本(如果沒有就挑最接近的)並複製其下方的網址
                5. 回到`Eclipse`，選擇選單列最右方的`Help → Install New Software...`

                    ![./README-media/23.png](./README-media/23.png)

                6. 將第四步複製的網址貼到第一個輸入框中，下方的列表會自己多出幾個選項，若沒有出現，可自行按`Enter`

                    ![./README-media/24.png](./README-media/24.png)

                7. 將列表出現的項目全選，可點右上方的`Select All`按鈕
                8. 點擊右下方的`Next >`按鈕即可開始安裝
        - 開啟專案
            1. 選擇選單列`File → import...`

                ![./README-media/25.png](./README-media/25.png)

            2. 選擇`Gradle → Existing Gradle Project`

                ![./README-media/26.png](./README-media/26.png)

            3. 選`Next >`兩次，看到以下畫面

                ![./README-media/27.png](./README-media/27.png)

            4. 將`Project root directory`選至專案根目錄，可用最右方的`Browse...`按鈕選擇
            5. 點右下方的`Next >`
            6. 將`Override workspace settings`打勾後選右下方的`Next >`

                ![./README-media/28.png](./README-media/28.png)

            7. 等待數秒後會在中間區塊列出他偵測到的專案有哪些，確認無誤後即可點右下方的`Finish`結束`import`

                ![./README-media/29.png](./README-media/29.png)

            8. 在專案視窗中會多出以下幾個專案

                ![./README-media/30.png](./README-media/30.png)

        - JDK
            1. 對專案點右鍵，在選單中用滑鼠指著`Build Path >` Configure Build Path...

                ![./README-media/31.png](./README-media/31.png)

            2. 選擇中間上方的Library

                ![./README-media/32.png](./README-media/32.png)

            3. 點擊下方的`JRE System Library`，並選擇右下方的`Edit`按鈕

                ![./README-media/33.png](./README-media/33.png)

            4. 選擇第二項`Alternate JRE`，並在旁邊的下拉選單選擇JDK 11，選擇好後即可按`Finish`、`Apply and Close`

                ![./README-media/34.png](./README-media/34.png)

                1. 若下拉選單中找不到JDK，可點選旁邊的`Installed JREs...`，再點選右方的`Add...`按鈕

                    ![./README-media/35.png](./README-media/35.png)

                2. 選擇`Standard VM`(理論上是預設)後，點下方的`Next >`按鈕

                    ![./README-media/36.png](./README-media/36.png)

                3. `JRE home`選至JDK根目錄後(可用右上方的`Directory...`選擇)，點擊下方`Finish`，接著選`Apply and Close`，下拉選單就會多出剛剛的JDK了

                    ![./README-media/37.png](./README-media/37.png)

        - Lombok
            1. 至`Lombok`官網[下載](https://projectlombok.org/download)最新版本(撰寫時最新版為：1.8.12)
            2. 點擊下載下來的`lombok.jar`
            3. 等待數秒後，中間的區塊會列出您電腦上所有安裝的`Eclipse`存放位置(若`Eclipse`已安裝過`Lombok`，則在路徑旁會有一根辣椒)

                ![./README-media/38.png](./README-media/38.png)

            4. 選擇要安裝`Lombok`的`Eclipse`後，點擊右下方的`Install / Update`開始安裝
            5. 出現以下畫面表示安裝成功，需重啟`Eclipse`才會生效

                ![./README-media/39.png](./README-media/39.png)
