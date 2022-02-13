Build Dependencies:
> - jdk 8
> - maven

build command:
> Run the following command on the project directory:<br>
> ```mvn clean package```

run command:
> Run the following command on the project driectory:<br>
> For Windows:
>> cd target\mono-gateway<br> 
>> run.bat
> 
> For Linux:
>> cd target/mono-gateway<br>
>> ./run.sh

config file:
> please find the config file on <br>
> ```{project_dir}/target/mono-gateway/resources/application.properties``` <br>
>> Config Explaination: <br> <br>
>> upsream.port = Port of Inbound <br>
>> downstream.ip = IP address of Outbount <br>
>> downstream.port = Port of Outbount <br>
>> orderProcessor.thread = Number of Thread for Order Logic Checking <br>
>> msgSender.msgPerSec = Outbound Message throttle <br>
