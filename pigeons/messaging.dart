import 'package:pigeon/pigeon.dart';

class Mymessage {
  final String title;
  final String body;
  final String email;

  Mymessage(this.title, this.body, this.email);
}

@HostApi()
abstract class MessageApi {
  List<Mymessage> getMessage(String fromemail);
}

class NotificationData {
  String? id;
  String? name;
  String? description;
}

@HostApi()
abstract class NotificationApi {
  bool createNotificationChannel(NotificationData data);
  bool openFile(String filePath);
}

