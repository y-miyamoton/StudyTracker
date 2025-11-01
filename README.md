# Manablog
学習テーマ毎の記録・可視化・振り返りを支援するWebアプリケーション。学びを記録して、成長を見える化する自分専用の学習ブログという意味を込めて、**Manabu(学ぶ) + Blog = Manablog**とした。
# アプリを作ったきっかけ
転職準備の中でJavaとSpring Bootの学習を進めてるうちに、インプットだけでは何を理解しているのか曖昧に感じた。そこでアウトプットの機会としてWebアプリケーションを実際に作ってみることにした。
受験勉強や資格勉強などを通じて学習を続けるモチベーションを維持することの難しさを感じた。そこで
学習量を定量的に把握できることでモチベーションを維持し、さらにポモドーロテクニックを用いて、集中する時間、休憩時間を設定し、学習の質も担保できるのではないかと考えた。
# アプリの主なページと機能
| トップ画面（ダッシュボード）| 科目一覧画面 |
|:----------------------:|:----------:|
| ![](https://storage.googleapis.com/zenn-user-upload/c4daff5bdc78-20251101.png)日、週、月、科目別で学習時間の集計 | ![](https://storage.googleapis.com/zenn-user-upload/b219d91969fb-20251101.png)登録している科目の一覧を表示 |

| 科目作成画面 | 科目詳細画面 |
|:----------:|:----------:|
| ![](https://storage.googleapis.com/zenn-user-upload/728856e3aa38-20251101.png)科目の作成機能を実装 | ![](https://storage.googleapis.com/zenn-user-upload/92f4b61a08f7-20251101.png)科目編集、削除機能を実装 |

| 記録一覧画面 | 記録詳細画面 |
|:----------:|:----------:|
| ![](https://storage.googleapis.com/zenn-user-upload/903a4f0a6fb5-20251101.png)学習記録の一覧の表示 | ![](https://storage.googleapis.com/zenn-user-upload/1c74e29ab5b3-20251101.png)学習記録の編集、削除機能を実装 |

| 記録手動登録画面 | タイマー設定画面 |
|:-------------:|:-------------:|
| ![](https://storage.googleapis.com/zenn-user-upload/c33cc778f764-20251101.png)手動で学習記録を登録できる機能を実装 | ![](https://storage.googleapis.com/zenn-user-upload/f43aae4a6ced-20251101.png)ポモドーロタイマーの機能を実装 |

| タイマー（集中）| タイマー（休憩）|
|:------------:|:------------:|
|![](https://storage.googleapis.com/zenn-user-upload/7220d7027161-20251101.png)時刻を表示|![](https://storage.googleapis.com/zenn-user-upload/606941dfa260-20251101.png)時刻を表示|
# 使用技術
- Java 21
- Spring Boot 3.5.6
- My Batis
- Postgresql
- Bootstrap 5 + Thymeleaf
- Gradle
# ER図
![](https://storage.googleapis.com/zenn-user-upload/0fc451245578-20251101.png)
# 今後の展望
- ログイン機能を追加する
  - Spring Securityで実装予定
  - ゲストログインできるようにする
- 目標設定できるようにする
- 時間経過後の通知機能を追加する
- テストコードを作成
