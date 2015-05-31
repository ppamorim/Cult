![Logo 1][10]

The Cult will provide a new layout for your Toolbar. This allow you to use with a custom SearchView with animation and more.
This library should work on API 14.

![Demo 1][11]

Usage
-----

You can use this library like a Toolbar, you just need to do the following:

* 1. Add ''CultView'' view to your root layout and add a reference for your
layout, look like:

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/blue">

    <com.github.ppamorim.cult.CultView
        android:id="@+id/cult_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:toolbar_height="64dp"
        app:content_view="@layout/layout_base"/>

</FrameLayout>
```

Sample
------

<a href="https://play.google.com/store/apps/details?id=com.github.ppamorim.cult">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

Import dependency
--------------------------------

* This library is under development yet, use at your own risk.

This library use `appcompat-v7:22.1.1` and `dmytrodanylyk.shadow-layout`.

But why not to add in MavenCentral?
Because it is so much bureaucratic.

JitPack is there and is the future!

Into your build.gradle:

```groovy

repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.ppamorim:cult:0.1'
}
```

Contributors
------------

* [Pedro Paulo de Amorim][3]

Developed By
------------

* Pedro Paulo de Amorim - <pp.amorim@hotmail.com>

<a href="https://www.linkedin.com/profile/view?id=185411359">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
</a>

Libraries used on the sample project
------------------------------------

* [Butterknife][5]
* [SmartTabLayout][6]

License
-------

    Copyright 2015 Pedro Paulo de Amorim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[3]: https://github.com/ppamorim/
[5]: https://github.com/JakeWharton/butterknife
[6]: https://github.com/ogaclejapan/SmartTabLayout
[10]: ./art/logo.png
[11]: ./art/cult_app_big.gif
