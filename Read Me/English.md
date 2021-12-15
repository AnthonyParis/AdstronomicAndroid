## [**🇫🇷 VERSION FRANÇAISE DISPONIBLE 🇫🇷**](https://github.com/AnthonyParis/AdstronomicAndroid/blob/master/Read%20Me/French.md)



# **Adstronomic - Installation Guide (Android)**



## <u>1 - Introduction</u>



Adstronomic is an advertising platform that allows you to leverage and synthesize your users' data to deliver the most relevant advertising to them. By taking into account the specificities of each game and its users, coupled with a revolutionary AI, we can identify the most relevant ads to increase your game's revenues. To achieve this, Adstronomic provides you with three key tools :

	- A web-based platform where you can set up your projects and associated ads.
	- An API that allows you to interact with Adstronomic's data.
	- An SDK that allows you to quickly and easily use the full potential of Adstronomic.

Each of these tools is intuitive, so you can focus on what matters most to you : The success of your project. In this guide, we will focus on the third point : Installing and using the SDK, here in its Android version. 📱

Please note that there are two ways to use Adstronomic : If you are starting a new project, we invite you to clone this repository directly, and use it as a working base. This way you can skip the section "2 - Installing Adstronomic". However, if your project is already well advanced, you may prefer to manually install the SDK to your existing project. In that case, the next section is for you !



## <u>2 - Installing Adstronomic</u>



As an Android developer, you are probably familiar with Android Studio, Google's development tool. So let's launch it, and create a new project.

You can start from any template you want. I'm going to choose "Empty Activity" to have the simplest project possible for this guide. Then, configure your project by putting the name, package name and location you want. Concerning the language, we will use Kotlin, and the Android 23 SDK. You can use a higher SDK if you want, but I advise you not to use a lower one, to avoid compatibility problems.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/1.png" alt="1" style="zoom:25%;" />

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/2.png" alt="2" style="zoom:25%;" />

Your project is now created. However, it is an empty project, which does not include Adstronomic. First, take a look at the left column. You should have Android selected at the top, and your package tree below it. Right click on app > java, and click on New > Package. Select the folder "../app/src/main/java" and specify the name "com.adstronomic.sdk". This package name is important, and should not be changed !

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/3.png" alt="3" style="zoom:25%;" />

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/4.png" alt="4" style="zoom:25%;" />

Note that since I already had the "com.adstronomic.sdk" package when I created the project, these two packages will be merged in Android Studio, but on your project, you should have an sdk package below your default package. Now open this sdk package.

We will now need the Adstronomic SDK sources. To do this go to the root of this repository, open the app > src > main > java > com > adstronomic > sdk folder, and copy its contents. Go back to your Android Studio project, and copy these files into the sdk package we just created. We'll have a few errors though, highlighted in red, which we'll fix in a moment.

Now we need to do the same thing for the resources. Go back to the root of this repository, and this time open the app > src > main > res folder, and copy its contents. On your Android Studio project, paste this content into the app > res folder. If some elements already existed, you can replace them, or merge them manually, depending on the state of your project.

If you have followed this far, you should have the following hierarchy in front of you :

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/5.png" alt="5" style="zoom:25%;" />

Again, on your own project, you will normally have a separate package for the SDK, and the MainActivity should be separate from the other folders.

Now we'll tackle the compilation errors present in our project. Indeed, the Android version of the Adstronomic SDK requires some additional libraries, which we will add right away.

To start with, we will get the "JSON Simple" library, which is used to parse JSON files. Open this repository, copy the content of the app > libs folder, go back to your project, and paste its content in the same location.

Then, let's add the libraries to the project. To do this, let's go back to Android Studio, and open the file "Gradle Scripts" > build.gradle. We normally have two build.gradle files, and we will modify the second one, the one that defines the modules used. We have to find the dependencies block, and insert the following lines :

```
implementation 'com.android.volley:volley:1.2.1'
implementation 'com.squareup.picasso:picasso:2.71828'
implementation files('/Users/Pythony/Projects/Kotlin/Adstronomic/app/libs/json-simple-1.1.jar'`
```

Remember to modify the last line so that it points to the file you just pasted, using an absolute path.

Finally, after having modified this file, a yellow banner should appear at the top of the screen, proposing us to synchronize the modifications by clicking on "Sync Now", which we will do.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/6.png" alt="6" style="zoom:25%;" />

Normally, this synchronization should remove the remaining errors.

We'll make sure the project is working properly by starting it. To do this, let's select an AVD from the list at the top, and then click the Run button to its right. If the Adstronomic SDK is correctly installed, an AVD should open, and display the following screen :

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/7.png" alt="7" style="zoom:25%;" />

Congratulations ! You have just installed Adstronomic in your project ! 🥳 The next step is now to configure it !



## <u>3 - Adstronomic Configuration</u>



Now that Adstronomic is integrated into our project, let's see how to set it up so that it retrieves and sends the right data.

To do this, let's start by opening the MainActivity file, which is contained in the project's default package. It contains the following code :

```kotlin
package com.adstronomic.sdk.android

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

Note that I will remove the imports in the examples given, for simplicity.

The first step is to initialize Adstronomic with a campaignID, via the following function :

```kotlin
Adstronomic.init(this, "wJMvF7kouz0lsO4m3d5a")
```

🚨 Please note that the second parameter is the campaign ID, as indicated on the [Adstronomic web platform](http://app.adstronomic.com). The ID shown here is a demo account, which you can use to check how well Adstronomic works, but which should definitely not be deployed in production !

For the rest, we will use a view with three buttons, each of which will launch a different type of ad. Of course, in a real project, you can launch your ads at any other event, keeping the same principle.

I invite you to use the same view as I did in the first place, by copying the app > src > main > res > layout > activity_main.xml file from this repository into your project, in the same location.

If you prefer, you can use your own view, adding three buttons, and adding the IDs bannerAdButton, interstitialAdButton and rewardedAdButton.

Finally, one last important step : Adstronomic will need access to the Internet to communicate with the API. So we need to allow it to do so, by adding the following line to the app > src > main > AndroidManifest.xml file.

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

Now that Adstronomic is set up correctly, we can start loading our ads !



## <u>4 - Loading a Banner Ad</u>



A BannerAd is simply an image ad, usually displayed at the bottom of the screen during a game. The advantage of this type of ad is that it does not block the rest of the screen, and can therefore be displayed throughout the game.

In Adstronomic, these ads are represented by images, of type ImageView. So we'll create one manually, and retrieve it from our MainActivity.

First, let's open the file res > layout > activity_main.xml, and add an ImageView, with the ID bannerAdImage. Usually, such an ad is present at the bottom of the screen, across the entire width, but you can change this, if needed.

Note that an ImageView is already present in the activity_main.xml of this repository, so you don't have to add one if you have duplicated this file.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/8.png" alt="8" style="zoom:25%;" />

We will now retrieve this image in our MainActivity. First, let's initialize an attribute that will reference it.

```kotlin
private lateinit var bannerAdImage: ImageView
```

Next, let's initialize this attribute to retrieve the ImageView via its ID.

```kotlin
bannerAdImage = findViewById(R.id.bannerAdImage)
```

Now that the image is ready, we'll also initialize and retrieve a button, which will act as a trigger. Add one if you don't have one in your view, and add the ID bannerAdButton to it.

Once the button is present, we'll declare it and initialize it via an attribute, just like we did with our ImageView.

```kotlin
private lateinit var bannerAdButton: Button
```

```kotlin
bannerAdButton = findViewById(R.id.bannerAdButton)
```

All our elements are now initialized, and all we have to do is load our ad. This is done simply by calling the loadBannerIntoImageView function, and using it like this :

```kotlin
Adstronomic.loadBannerIntoImageView(this, bannerAdImage)
```

Note that the second argument of this function is the reference to our ImageView.

Where to call this function ? Well, that's up to you. You can call it from the beginning, so that your ad appears as soon as it is launched, or wait for a specific action. Since we are on a test project here, I made sure to call it when the button we just created is clicked. But you can call it at any other event in our application.

If you have followed these steps correctly, you should have the following code in front of you :

```kotlin
package com.adstronomic.sdk.android

class MainActivity : AppCompatActivity() {
    private lateinit var bannerAdImage: ImageView
    private lateinit var bannerAdButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Adstronomic.init(this, "wJMvF7kouz0lsO4m3d5a")

        bannerAdImage = findViewById(R.id.bannerAdImage)
        bannerAdButton = findViewById(R.id.bannerAdButton)

        bannerAdButton.setOnClickListener {
            Adstronomic.loadBannerIntoImageView(this, bannerAdImage)
        }

    }
}
```

And when launching our AVD, our Banner Ad should appear at the bottom of the screen :

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/9.png" alt="9" style="zoom:25%;" />



## <u>5 - Loading an Interstitial Ad</u>



If you have successfully displayed a Banner Ad, you should have no difficulty displaying Interstitial Ad and Rewarded Ad, as they are used in almost the same way. Let's go back to the activity_main.xml, and add a VideoView this time, with the ID interstitialAdVideo. As the name suggests, this is the equivalent of an ImageView, for videos.

Note that this component takes all the space on the screen by default. So we will make its visibility invisible for now.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/10.png" alt="10" style="zoom:25%;" />

Once this is done, we will create, initialize, and listen to a button, exactly as we did for the Banner Ad. To do this, let's add the following lines to our MainActivity :

```kotlin
private lateinit var interstitialAdVideo: VideoView
private lateinit var interstitialAdButton: Button
```

```kotlin
interstitialAdVideo = findViewById(R.id.interstitialAdVideo)
interstitialAdButton = findViewById(R.id.interstitialAdButton)
```

```kotlin
interstitialAdButton.setOnClickListener {
    Adstronomic.loadInterstitialIntoVideoView(this, interstitialAdVideo)
}
```

Only one particular point : As we changed the visibility of the VideoView at startup, we have to remember to make it visible again, when the video starts.

```kotlin
interstitialAdVideo.visibility = View.VISIBLE
```

```kotlin
package com.adstronomic.sdk.android

class MainActivity : AppCompatActivity() {
    private lateinit var bannerAdImage: ImageView
    private lateinit var bannerAdButton: Button

    private lateinit var interstitialAdVideo: VideoView
    private lateinit var interstitialAdButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Adstronomic.init(this, "wJMvF7kouz0lsO4m3d5a")

        bannerAdImage = findViewById(R.id.bannerAdImage)
        bannerAdButton = findViewById(R.id.bannerAdButton)

        bannerAdButton.setOnClickListener {
            Adstronomic.loadBannerIntoImageView(this, bannerAdImage)
        }

        interstitialAdVideo = findViewById(R.id.interstitialAdVideo)
        interstitialAdButton = findViewById(R.id.interstitialAdButton)

        interstitialAdButton.setOnClickListener {
            interstitialAdVideo.visibility = View.VISIBLE
            Adstronomic.loadInterstitialIntoVideoView(this, interstitialAdVideo)
        }

    }
}
```

By starting the application and clicking on the corresponding button, we should have our Interstitial Ad launching in full screen.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/11.png" alt="11" style="zoom:25%;" />



## <u>6 - Loading a Rewarded Ad</u>



Ready for the final step ?

Rewarded Ads are video ads similar to Interstitial Ads. The difference is that they have a different marketing goal, and are more about rewarding a specific action. Technically speaking, they work exactly the same, except that you need to call the loadRewardedIntoVideoView method instead of loadInterstitialIntoVideoView, and create and initialize a new button and VideoView.

Since these changes are really simple, you should end up with a result similar to the following code :

```kotlin
package com.adstronomic.sdk.android

class MainActivity : AppCompatActivity() {
    private lateinit var bannerAdImage: ImageView
    private lateinit var bannerAdButton: Button

    private lateinit var interstitialAdVideo: VideoView
    private lateinit var interstitialAdButton: Button

    private lateinit var rewardedAdVideo: VideoView
    private lateinit var rewardedAdButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Adstronomic.init(this, "wJMvF7kouz0lsO4m3d5a")

        bannerAdImage = findViewById(R.id.bannerAdImage)
        bannerAdButton = findViewById(R.id.bannerAdButton)

        bannerAdButton.setOnClickListener {
            Adstronomic.loadBannerIntoImageView(this, bannerAdImage)
        }

        interstitialAdVideo = findViewById(R.id.interstitialAdVideo)
        interstitialAdButton = findViewById(R.id.interstitialAdButton)

        interstitialAdButton.setOnClickListener {
            interstitialAdVideo.visibility = View.VISIBLE

            Adstronomic.loadInterstitialIntoVideoView(this, interstitialAdVideo)
        }

        rewardedAdVideo = findViewById(R.id.rewardedAdVideo)
        rewardedAdButton = findViewById(R.id.rewardedAdButton)

        rewardedAdButton.setOnClickListener {
            rewardedAdVideo.visibility = View.VISIBLE

            Adstronomic.loadRewardedIntoVideoView(this, rewardedAdVideo)
        }

    }
}
```

When launching our AVD, we should see our Rewarded Ad appear before our eyes, in the same way as the Interstitial Ad in the previous section.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/12.png" alt="12" style="zoom:25%;" />



## <u>7 - Conclusion</u>



If you're reading this, you should have fully installed, configured and integrated the Adstronomic SDK into your project.

As you can see, once this step is complete, all you have to do is create a campaign [on our web platform](http://app.adstronomic.com), add your ads, and indicate your campaignID when the application loads.

Of course, Adstronomic is still young, and will evolve over time. Therefore, it is important that you give us feedback on the features you would like to see, or simply tell us what you think of us.

Whether it's a simple message of thanks, or a seemingly trivial question, we read and take the time to answer all your messages.

So don't wait any longer, and write to us at <hello@adstronomic.com> !
