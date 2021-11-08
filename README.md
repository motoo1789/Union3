# Union3

Union3はEclipseの拡張機能として実装できます．
Eclipseで開発中のプロジェクトからデザインパターンのエラー（パターンエラー）を可視化するプラグインです．


## 主な機能
- 開発中のプロジェクトからパターンエラーの検出
- 検出したエラーをプラグイン上に可視化


## 動作環境
- Eclipseのver Photon以降
- javaSE-1.8またはそれ以降

### 依存関係
本プラグインは以下のプラグインと依存関係を持ちます．
- org.eclipse.ui(3.109.100)
- org.eclipse.core.runtime(3.14.0)
- org.eclipse.core.resources(3.13.0)
- org.eclipse.ui.ide(3.14.0)
- org.eclipse.ui.editors(3.11.100)
- org.eclipse.jface.text(3.13.0)
- org.eclipse.jdt.core(3.14.0)
- org.eclipse.jdt.ui(3.14.0)
- org.apache.commons.io

## 導入方法
以下の手順にしたがってプラグインを導入してください．

### 1. プラグインの導入
**Union3_1.0.0.202011081430.jar**ファイルをeclipseのpluginsディレクトリにコピーする．

### 2. ビューの追加
1. Eclipseの Window メニューから Show View ， Other... を選択
2. 7期生5班プラグインからエラー表示用ビュー3を選択

### 3. ユーザーによる選択
検出したパターンとディレクトリ(プロジェクト直下)をコンポーネントから選択する．
その後右上の検出開始を押すと実行される．

### 4. エラーの修正
検出結果は表形式の◯✕で表される．
エラーの箇所は✕で表記されるのでプラグインの指示に従って修正をする
