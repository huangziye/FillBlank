
[![](https://jitpack.io/v/huangziye/FillBlank.svg)](https://jitpack.io/#huangziye/FillBlank)

# Add ` FillBlank ` to project

- Step 1：Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```android
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

- Step 2：Add the dependency

The latest version shall prevail.

```android
dependencies {
        implementation 'com.github.huangziye:FillBlank:${latest_version}'
}
```

# How to use it


```kotlin
val fillBlankView = findViewById<FillBlankView>(R.id.fillBlankView)
val content =
    "纷纷扬扬的________下了半尺多厚。天地间________的一片。我顺着________工地走了四十多公里，" + "只听见各种机器的吼声，可是看不见人影，也看不见工点。一进灵官峡，我就心里发慌。"

// 答案范围集合
val rangeList = mutableListOf<AnswerRange>()
rangeList.add(AnswerRange(5, 13))
rangeList.add(AnswerRange(23, 31))
rangeList.add(AnswerRange(38, 46))

fillBlankView.setData(content, rangeList, Color.RED)
```

### [reference](https://github.com/alidili/Demos/blob/301f52b9a9b6f511e199044d03f8c9fc75e6d810/FillBlankQuestionDemo/app/src/main/java/com/yl/fillblankquestiondemo/MainActivity.java)



# About me


- [简书](https://user-gold-cdn.xitu.io/2018/7/26/164d5709442f7342)

- [掘金](https://juejin.im/user/5ad93382518825671547306b)

- [Github](https://github.com/huangziye)




# License

```
Copyright 2018, huangziye

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



