import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pigon_demo/pigeon.dart';

import 'pigeon.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
   final eventChannel =
      const EventChannel('com.example.pigeon_demo/number_events');
  int currentNumber = 0;

  @override
  void initState() {
    super.initState();
    eventChannel.receiveBroadcastStream().listen(_onNumberReceived);
  }

  void _onNumberReceived(dynamic numberData) {
    try {
      if (numberData is CustomNumber) {
        setState(() {
          currentNumber = numberData.number!; // Update the current number
        });
      } else {
        debugPrint("Number is -: $numberData");
      }
    } on Exception catch (e) {
      debugPrint("Unknown data exception: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Pigeon Demo'),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          InkWell(
            onTap: () {
              debugPrint('clicked on button');
              _getpigeonMessage();
            },
            child: const Text('Click me', style: TextStyle(fontSize: 30)),
          )
        ],
      ),
    );
  }

  void _getpigeonMessage() async {
    debugPrint('hello');
    List<Mymessage> msglist = await MessageApi().getMessage("jaiho@gmail.com");
    for (var message in msglist) {
      debugPrint("retrieved body: ${message.body}");
      debugPrint("retrieved title: ${message.title}");
      debugPrint("retrieved email: ${message.email}");
    }

    debugPrint('FOR NOTIFICATION TEST ');
    bool ischannelcreated = await NotificationApi().createNotificationChannel(
        NotificationData(
            id: "1", name: "name", description: "description is here"));
    log('channel created or not $ischannelcreated');
  }
}
