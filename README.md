# Using the Android PCI SDK

To use the Android PCI SDK, simply add the `PCIView` to your layout:

```xml
<com.aptopayments.sdk.pci.PCIView
    android:id="@+id/pci_view"
    android:layout_width="match_parent"
    android:layout_height="300dp" />
```

and add a property in your fragment/activity:

```kotlin
private lateinit var pciView: PCIView

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    pciView = findViewById(R.id.pci_view)
}
```

Then, initialize the SDK using the initialize medhod:

```kotlin
pciView.initialise(
    apiKey = "API_KEY",
    userToken = "USER_TOKEN",
    cardId = "CARD_ID",
    lastFour = "1234",
    environment = "sandbox"
)
```

Note: The allowed values for the `environment` parameter are `sandbox` and `production`.

To show the card's last four, use the following snippet:

```kotlin
pciView.lastFour()
```

To show the card's complete data, use the following snippet:

```kotlin
pciView.reveal()
```

The PCI SDK will verify the user (if needed) and will show the card data.

## Customise the PCI SDK

The PCI SDK look & feel can be customised in different ways:

### Showing / hiding elements

You can decide which elements are shown by the PCI SDK. There are three elements that can be shown / hidden:

1. PAN
1. CVV
1. Exp. Date

You can, also, define the css styles of various components, including:

1. IFrame (container)
1. PAN field.
1. CVV field.
1. Exp. Date field

To customise the PCI look and feel, you can use the following snippet:

```kotlin
pciView.showPan = true
pciView.showCvv = false
pciView.showExp = false
pciView.styles = [
  "container": "IFrame CSS Styles",
  "content": [
    "pan": "PAN CSS Styles",
    "cvv": "CVV CSS Styles",
    "exp": "Exp. Date CSS Styles"
  ]
]
```

## Author

Pau Teruel, pau@aptopayments.com

## License

AptoPCISDK is available under the MIT license. See the LICENSE file for more info.