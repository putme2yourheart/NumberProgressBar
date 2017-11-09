# NumberProgressBar

![image](https://github.com/putme2yourheart/NumberProgressBar/raw/master/screenshots/sample.gif)


Place the view in your XML layout file.

```xml
    <io.github.putme2yourheart.numberprogressbar.NumberProgressBar
        android:id="@+id/numberpb"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    I strongly recommend that you use android:layout_height="wrap_content"
    If you want to change the bar height please use app:npb_progress_bar_height
```

## Theming

There are several theming options available through XML attributes which you can use to completely change the look-and-feel of this view to match the theme of your app.

```xml
  app:npb_progress_current="0"                  // set the current progress, default 0
  app:npb_progress_max="100"                    // set the max progress, default 100
  app:npb_progress_bar_height="3dp"             // set the progress bar height, default 3dp
  app:npb_reach_color="#27AE60"                 // set the reach color, default #27AE60
  app:npb_unreached_color="#CDCDCD"             // set the unreached color, default #CDCDCD
  app:npb_text_visible="true"                   // should show the percent text, default is true
  app:npb_text_size="14sp"                      // set the progress percent text size, default 14sp
  app:npb_text_color=""                         // set the text color. when the progress height < text size, text color is #27AE60, or white
```

# License

```
Copyright 2017 putme2yourheart

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
