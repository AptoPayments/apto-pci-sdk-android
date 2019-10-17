(function(){function r(e,n,t){function o(i,f){if(!n[i]){if(!e[i]){var c="function"==typeof require&&require;if(!f&&c)return c(i,!0);if(u)return u(i,!0);var a=new Error("Cannot find module '"+i+"'");throw a.code="MODULE_NOT_FOUND",a}var p=n[i]={exports:{}};e[i][0].call(p.exports,function(r){var n=e[i][1][r];return o(n||r)},p,p.exports,r,e,n,t)}return n[i].exports}for(var u="function"==typeof require&&require,i=0;i<t.length;i++)o(t[i]);return o}return r})()({1:[function(require,module,exports){
const AptoPCISDK = require('./apto-pci-sdk').AptoPCISDK;
window.AptoPCISDK = new AptoPCISDK(document.getElementById('apto-iframe').contentWindow);
},{"./apto-pci-sdk":2}],2:[function(require,module,exports){
class AptoPCISDK {
    constructor (window) {
        this.window = window;
        this.initialise = this.initialise.bind(this);
        this.customisation = this.customisation.bind(this);
        this.lastFour = this.lastFour.bind(this);
        this.obfuscate = this.obfuscate.bind(this);
        this.reveal = this.reveal.bind(this);
    }

    initialise(apiKey, userToken, cardId, lastFour, environment) {
        const initialise = JSON.stringify({
            event: 'AptoPCISDK:initialise',
            apiKey: apiKey,
            userToken: userToken,
            cardId: cardId,
            lastFour: lastFour,
            environment: environment
        });
        this.window.postMessage(initialise, '*');
    }

    lastFour() {
        const lastFourEvent = JSON.stringify({event: 'AptoPCISDK:lastFour'});
        this.window.postMessage(lastFourEvent, '*');
    }

    obfuscate() {
        const obfuscateEvent = JSON.stringify({event: 'AptoPCISDK:obfuscate'});
        this.window.postMessage(obfuscateEvent, '*');
    }

    reveal() {
        const revealEvent = JSON.stringify({event: 'AptoPCISDK:reveal'});
        this.window.postMessage(revealEvent, '*');
    }

    customisation(flags, styles) {
        const flagsParsed = JSON.parse(flags);
        const stylesParsed = JSON.parse(styles);
        const customisationEvent = JSON.stringify({event: 'AptoPCISDK:customisation', flags: flagsParsed, styles: stylesParsed});
        this.window.postMessage(customisationEvent, '*');
    }
}

module.exports = { AptoPCISDK };
},{}]},{},[1]);
