## [**🇺🇸 ENGLISH VERSION AVAILABLE 🇺🇸**](https://github.com/AnthonyParis/AdstronomicAndroid/blob/master/Read%20Me/English.md)



# **Adstronomic - Guide d'installation (Android)**



## <u>1 - Introduction</u>



Adstronomic est une plateforme publicitaire vous permettant d'exploiter et de synthétiser les données de vos utilisateurs, afin de leur proposer la publicité la plus adaptée à leurs besoins. En nous appuyant sur les spécificités de chaque jeu et de ses utilisateurs, couplé à une IA révolutionnaire, nous parvenons à identifier les publicités les plus pertinentes, afin d'augmenter les revenus de votre jeu. Pour y parvenir, Adstronomic met à votre disposition trois outils clé :

	- Une plateforme web sur laquelle vous pouvez paramètrer vos projets, et les publicités associées.
	- Une API permettant d'interagir avec les données d'Adstronomic.
	- Un SDK qui vous permet d'utiliser facilement et rapidement tout le potentiel d'Adstronomic.

Chacun de ces outil est intuitif, afin de vous permettre de vous concentrer sur ce qui compte le plus pour vous : La réussite de votre projet. Dans ce guide, nous allons nous concentrer sur le troisième point : L'installation et l'utilisation du SDK, ici dans sa version Android. 📱

Veuillez noter que deux solutions s'offrent à vous pour utiliser Adstronomic : Si vous commencez un nouveau projet, nous vous invitons à cloner directement ce dépôt, et à l'utiliser comme base de travail. Vous pourrez ainsi sauter la section "2 - Installation d'Adstronomic". Toutefois, si votre projet est déjà bien avancé, vous préférerez sûrement installer manuellement le SDK à votre projet existant. Dans ce cas là, la section suivante est faite pour vous !



## <u>2 - Installation d'Adstronomic</u>



En tant que développeur Android, vous êtes sûrement familier avec Android Studio, l'outil de développement de Google. Nous allons donc le lancer, et créer un nouveau projet.

Vous pouvez démarrer depuis le template que vous voulez. Je vais choisir le "Empty Activity" pour avoir un projet le plus simple possible pour ce guide. Ensuite, configurez votre projet en mettant le nom, nom de package et emplacement que vous voulez. Concernant le langage, nous allons utilisez du Kotlin, et le SDK Android 23. Vous pouvez utilisez un SDK supérieur si vous le souhaitez, mais je vous déconseille d'en prendre un inférieur, pour ne pas avoir de soucis de compatibilité.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/1.png" alt="1" style="zoom:25%;" />

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/2.png" alt="2" style="zoom:25%;" />

Votre projet est maintenant crée. Toutefois, il s'agit d'un projet vide, qui n'intègre pas Adstronomic. Tout d'abord, jetez un oeil à la colonne de gauche. Vous devriez avoir Android de sélectionné tout en haut, et l'arborescence de vos différents packages en dessous. Faîtes un clic droit sur app > java, et cliquez sur New > Package. Sélectionnez le dossier "../app/src/main/java" et indiquez le nom "com.adstronomic.sdk". Ce nom de package est important, et ne doit pas être modifié !

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/3.png" alt="3" style="zoom:25%;" />

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/4.png" alt="4" style="zoom:25%;" />

Notez que comme j'avais déjà le package "com.adstronomic.sdk" à la création du projet, ces deux packages seront fusionnés dans Android Studio, mais sur votre projet à vous, vous devriez avoir un package sdk en dessous de votre package par défaut. Ouvrez maintenant ce package sdk.

Nous allons maintenant avoir besoin des sources du SDK Adstronomic. Pour cela allez à la racine de ce dépôt, ouvrez le dossier app > src > main > java > com > adstronomic > sdk, et copiez son contenu. Retournez sur votre projet Android Studio, et copiez ces fichiers dans le package sdk que nous venons de créé. Nous aurons cependant quelques erreurs, soulignées en rouge, que nous corrigerons dans un instant.

Maintenant, nous devons faire la même chose pour les resources. Retournez à la racine de ce dépôt, et ouvrez cette fois ci le dossier app > src > main > res, et copiez son contenu. Sur votre projet Android Studio, collez ce contenu dans le dossier app > res. Si certains éléments existaient déjà, vous pouvez les remplacer, ou les fusionner manuellement, en fonction de l'état de votre projet.

Si vous avez suivi jusque là, vous devriez avoir la hiérarchie suivante sous les yeux :

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/5.png" alt="5" style="zoom:25%;" />

Encore une fois, sur votre propre projet, vous aurez normalement un package distinct pour le SDK, et le MainActivity devrait être séparé des autres dossiers.

Maintenant, nous allons nous attaquer aux erreurs de compilation présentes dans notre projet. En effet, la version Android du SDK Adstronomic nécessite quelques libraires supplémentaires, que nous allons ajouter de suite.

Pour commencer, nous allons récupérer la librairie "JSON Simple", qui est utilisée pour analyser les fichiers JSON. Ouvrez ce dépot, copiez le contenu du dossier app > libs, retournez dans votre projet, et collez son contenu au même emplacement.

Ensuite, ajoutons les librairies au projet. Pour cela, retournons sur Android Studio, et ouvrons le fichier "Gradle Scripts" > build.gradle. Nous avons normalement deux fichiers build.gradle, et nous allons modifier le second, celui qui définit les modules utilisés. Nous devons y trouver le bloc dependencies, et y insérer les lignes suivantes :

```
implementation 'com.android.volley:volley:1.2.1'
implementation 'com.squareup.picasso:picasso:2.71828'
implementation files('/Users/Pythony/Projects/Kotlin/Adstronomic/app/libs/json-simple-1.1.jar'`
```

Pensez à modifier la dernières lignes pour qu'elle pointe vers le fichier que vous venez de coller, en utilisant un chemin absolu.

Enfin, après avoir modifié ce fichier, un bandeau jaune devrait apparaître en haut de l'écran, nous proposant de synchroniser les modifications en cliquant sur "Sync Now", ce que nous allons faire.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/6.png" alt="6" style="zoom:25%;" />

Normalement, cette synchronisation devrait supprimer les erreurs restantes.

Nous allons nous assurer que le projet fonctionne correctement en le démarrant. Pour cela, sélectionnons un AVD dans la liste tout en haut, puis cliquons sur le bouton Run à sa droite. Si le SDK Adstronomic est correctement installé, un AVD devrait s'ouvrir, et afficher l'écran suivant :

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/7.png" alt="7" style="zoom:25%;" />

Félicitations ! Vous venez d'installer Adstronomic dans votre projet ! 🥳 La prochaine étape est maintenant de le configurer !



## <u>3 - Configuration d'Adstronomic</u>



Maintenant qu'Adstronomic est intégré à notre projet, nous allons voir comment le paramétrer pour qu'il récupère et envoie les bonnes données.

Pour cela, commençons par ouvrir le fichier MainActivity, contenu dans le package par défaut du projet. Celui-ci contient le code suivant :

```kotlin
package com.adstronomic.sdk.android

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

Notez que j'enlèverai les imports dans les exemples donnés, par soucis de simplicité.

La première étape est d'initialiser Adstronomic avec un campaignID, via la fonction suivante :

```kotlin
Adstronomic.init(this, "wJMvF7kouz0lsO4m3d5a")
```

🚨 Notez bien que le second paramètre correspond à l'identifiant de votre campagne, tel qu'indiqué sur la [plateforme web d'Adstronomic](http://app.adstronomic.com). L'identifiant indiqué ici correspond à un compte de démonstration, qui peut vous servir à vérifier le bon fonctionnement d'Adstronomic, mais qui ne doit absolument pas être déployé en production !

Pour le reste, nous allons utiliser une vue comprenant trois boutons, qui lanceront chacun un type de publicité différente. Bien sûr, dans un véritable projet, vous pourrez lancer vos publicités à n'importe quel autre événement, en gardant le même principe.

Je vous invite à utiliser la même vue que moi dans un premier temps, en copiant le fichier app > src > main > res > layout > activity_main.xml de ce dépôt dans votre projet, au même emplacement.

Si vous préférez, vous pouvez utilisez votre propre vue, en y ajoutant trois boutons, et en leur ajoutant les ID bannerAdButton, interstitialAdButton et rewardedAdButton.

Enfin, une dernière étape importante : Adstronomic va avoir besoin d'accéder à Internet pour communiquer avec l'API. Nous devons donc l'y autoriser, en ajoutant la ligne suivante dans le fichier app > src > main > AndroidManifest.xml.

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

Maintenant qu'Adstronomic est correctement configuré, nous allons pouvoir charger nos publicités !



## <u>4 - Chargement d'une Banner Ad</u>



Une BannerAd est tout simplement une publicité sous forme d'image, habituellement affichée au bas de l'écran pendant une partie. L'intérêt de ce type de publicité est qu'elle ne bloque pas le reste de l'écran, et peut donc être affichée pendant toute une partie.

Dans Adstronomic, ces publicités sont représentées par des images, de type ImageView. Nous allons donc en créer une manuellement, et la récupérer dans notre MainActivity.

Ouvrons tout d'abord le fichier res > layout > activity_main.xml, et ajoutons-y une ImageView, possédant l'ID bannerAdImage. Habituellement, une telle publicité est présente en bas de l'écran, sur toute sa largeur, mais vous pouvez changer cela, si besoin.

Notez qu'une ImageView est déjà présente dans le activity_main.xml de ce dépôt, et que vous n'avez donc pas à en ajouter un si vous avez dupliqué ce fichier.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/8.png" alt="8" style="zoom:25%;" />

Nous allons maintenant récupérer cette image dans notre MainActivity. Tout d'abord, initialisons un attribut qui va la référencer.

```kotlin
private lateinit var bannerAdImage: ImageView
```

Ensuite, initialisons cette attribut pour récupérer l'ImageView via son ID.

```kotlin
bannerAdImage = findViewById(R.id.bannerAdImage)
```

Maintenant que l'image est prête, nous allons également initialiser et récupérer un bouton, qui va servir de déclencheur. Ajoutez-en un si vous n'en avez pas dans votre vue, et ajouter lui l'ID bannerAdButton.

Une fois ce bouton présent, nous allons le déclarer et l'initialiser via un attribut, exactement comme nous l'avons fait avec notre ImageView.

```kotlin
private lateinit var bannerAdButton: Button
```

```kotlin
bannerAdButton = findViewById(R.id.bannerAdButton)
```

Tous nos éléments sont maintenant initialisés, et il ne nous reste plus qu'à charger notre publicité. Cela se fait tout simplement en appelant la fonction loadBannerIntoImageView, et en l'utilisant comme ceci :

```kotlin
Adstronomic.loadBannerIntoImageView(this, bannerAdImage)
```

Noter que le second argument de cette fonction est la référence vers notre ImageView.

Où appeler cette fonction ? Et bien, c'est à vous de voir. Vous pouvez l'appeler dès le début, afin que votre publicité apparaisse dès le lancement, ou attendre une action spécifique. Comme nous sommes ici sur un projet de test, j'ai fait en sorte de l'appeler au clic sur le bouton que nous venons de créer. Mais vous pouvez l'appeler à n'importe quel autre événement de notre application.

Si vous avez correctement suivi ces étapes, vous devriez avoir le code suivant sous les yeux :

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

Et en lançant notre AVD, notre Banner Ad devrait apparaître en bas de l'écran :

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/9.png" alt="9" style="zoom:25%;" />



## <u>5 - Chargement d'une Interstitial Ad</u>



Si vous avez réussi à afficher une Banner Ad, vous ne devriez pas avoir de difficulté pour l'affichage des Interstitial Ad et des Rewarded Ad, car elles s'utilisent presque de la même manière. Retournons dans le activity_main.xml, et ajoutons-y cette fois une VideoView, avec l'ID interstitialAdVideo. Comme son nom l'indique, il s'agit de l'équivalent d'une ImageView, pour les vidéos.

Notez que ce composant prends par défaut toute la place à l'écran. Nous allons donc mettre sa visibility à invisible pour l'instant.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/10.png" alt="10" style="zoom:25%;" />

Une fois cela fait, nous allons créer, initialiser, et mettre sur écoute un bouton, exactement comme nous l'avons fait pour la Banner Ad. Pour cela, ajoutons les lignes suivantes dans notre MainActivity :

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

Un seul point particulier : Comme nous avons changé la visibilité de la VideoView au démarrage, il faut penser à la rendre visible à nouveau, lorsque la vidéo démarre.

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

En démarrant l'application et en cliquant sur le bouton correspondant, nous devrions avoir notre Interstitial Ad qui se lance en plein écran.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/11.png" alt="11" style="zoom:25%;" />



## <u>6 - Chargement d'une Rewarded Ad</u>



Prêts pour la dernière étape ?

Les Rewarded Ad sont des publicités vidéo semblables aux Interstitial Ad. La différence est que celles-ci ont un but marketing différent, et visent plus à récompenser une action spécifique. Techniquement parlant, leur fonctionnement est rigoureusement identique, sauf qu'il faut appeler la méthode loadRewardedIntoVideoView à la place de loadInterstitialIntoVideoView, et créer et initialiser un nouveau bouton et une nouvelle VideoView.

Ces changements étant vraiment simples, vous devriez arriver à un résultat similaire au code suivant :

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

En lançant notre AVD, nous devrions voir notre Rewarded Ad apparaître sous nos yeux, de la même façon que l'Interstitial Ad à la section précédente.

<img src="https://raw.githubusercontent.com/AnthonyParis/AdstronomicAndroid/master/Read%20Me/12.png" alt="12" style="zoom:25%;" />



## <u>7 - Conclusion</u>


Si vous lisez ces lignes, vous avez normalement entièrement installé, configuré et intégré le SDK Adstronomic à votre projet.

Vous l'aurez compris, une fois cette étape terminée, il ne vous restera qu'à créer une campagne [sur notre plateforme web](http://app.adstronomic.com), y ajouter vos publicités, et indiquez votre campaignID au chargement de l'application.

Naturellement, Adstronomic est encore jeune, et sera amené à évoluer au fil du temps. Pour cela, il est primordial que vous nous fassiez des retours concernant les fonctionnalités que vous voudriez y trouver, ou tout simplement nous dire ce que vous pensez de nous.

Que cela soit un simple message de remerciement, ou une question en apparence anodine, nous lisons et prenons le temps de répondre à tous vos messages.

Alors n'attendez plus, et écrivez nous à <hello@adstronomic.com> !
