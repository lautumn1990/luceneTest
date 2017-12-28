# IK分词器安装

IK分词是林良益前辈自定义写的一个专门针对中文分词的分析器,最新版本为2012年的版本for4.0之后未做更新，后续版本lucene的接口改变使其不支持，所以需要进行修改

[Lucene6.5.0 下中文分词IKAnalyzer编译和使用](http://blog.csdn.net/fanpei_moukoy/article/details/67637851)

运行以下命令安装本地仓库

```shell
mvn install:install-file -Dfile=IKAnalyzer6.5.0.jar -DgroupId=org.wltea.analyzer -DartifactId=IKAnalyzer -Dversion=6.5.0 -Dpackaging=jar
```