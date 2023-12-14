# EfficientAir

This is the backend server of our EfficientAir project, the final project for EE542. 
<br> Current supported endpoints:
<br>
| Endpoints  | Function |
| ------------- | ------------- |
| /allhistory | shows all history for all sensor readings (warning: large json) |
| /last12h?id={sensor_id} | shows last 12 hours of readings from sensor_id |
| /readings | shows latest readings from all sensor ids |
| /allupdates | shows all thermostat setting history |
| /getcurrent | get current thermostat settings for all sensors |
| /update | update all thermostat settings: see documentations below |

<br>
/update endpoint updates all the current thermostat settings for all sensors. This is supposed to be called from AWS EventWatch with Lambda, to ensure correct update interval. Calling it prematurely will cause "unexpected" results.
<br><br>
Further documentation and explanation of the algorithm is in the video.
https://youtu.be/lj1FBwhl5D8
