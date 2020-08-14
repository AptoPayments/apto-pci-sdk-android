# Using the Android PCI SDK

The PCI SDK provides a transparent View that can handle the PCI data using a webView.

### Installation (Using [Gradle](https://gradle.org))

1. On your project root build.gradle add the Apto repository and Jcenter:
```
allprojects {
  repositories {
    ...
    maven { url "https://dl.bintray.com/apto/maven" }
    ...
  }
}
```

2. On your library module's `build.gradle` add the following dependency:

```
implementation 'com.aptopayments.sdk:mobile:1.0.2'
```

## Using the SDK

To use the Android PCI SDK, simply add the `PCIView` to your layout:

```xml
<com.aptopayments.sdk.pci.PCIView
    android:id="@+id/pci_view"
    android:layout_width="match_parent"
    android:layout_height="300dp" />
```

Then, in your view layer initialize the SDK using the initialize medhod:

```kotlin
pciView.initialise(
    apiKey = "API_KEY",
    userToken = "USER_TOKEN",
    cardId = "CARD_ID",
    lastFour = "1234",
    environment = "sbx"
)
```

Note: The allowed values for the `environment` parameter are  `sbx` and `prd`.

To show the card's last four, use the following snippet:

```kotlin
pciView.lastFour()
```

To show the card's complete data, use the following snippet:

```kotlin
pciView.reveal()
```

The PCI SDK will verify the user (if needed) sending an SMS and will show the card data once the verification process is completed.

## Customise the PCI SDK

The PCI SDK look & feel can be customised in different ways:

### Showing / hiding elements

You can decide which elements are shown by the PCI SDK. There are three elements that can be shown / hidden:

1. PAN
1. CVV
1. Exp. Date
1. "CVV" legend
1. "EXP" legend

You can, also, define the css styles of various components, including:

1. IFrame (container)
1. PAN field.
1. CVV field.
1. Exp. Date field

### Styling elements

To customise the PCI look and feel, you can use the following snippet:

```kotlin
pciView.showPan = true
pciView.showCvv = false
pciView.showExp = false
pciView.isCvvVisible = true
pciView.isExpVisible = true
pciView.styles = mapOf(
    "container" to "color: red",
    "content" to mapOf(
        "pan" to "color: blue",
        "cvv" to "color: yellow",
        "exp" to "color: green"
    )
)
```

- showPan : when set to true the pan will be shown
- showCvv : when set to true the cvv will be shown
- showExp : when set to true the expiration will be shown
- isCvvVisible : when set to true the "CVV" legend will be shown
- isExpVisible : when set to true the "EXP" legend will be shown

### Text customization

To customize the different texts shown in alerts, you can use the following attribute:

```kotlin
pciView.alertTexts = mapOf(
    "inputCode.message" to "What's your secret code?",
    "inputCode.okAction" to "Ok",
    "inputCode.cancelAction" to "Cancel",
    "wrongCode.message" to "Invalid code",
    "wrongCode.okAction" to "Ok"
)
```

### Dialog customization

By default the buttons that are shown in the Dialog to enter the SMS use your configured colorAccent but you can also configure them manually them:

```kotlin
pciView.alertButtonColor = yourIntColor
```

## License

AptoPCISDK is available under the MIT license. See the LICENSE file for more info.
