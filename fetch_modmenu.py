import urllib.request
import json

url = "https://maven.terraformersmc.com/releases/com/terraformersmc/modmenu/maven-metadata.xml"
try:
    response = urllib.request.urlopen(url)
    data = response.read().decode('utf-8')
    print(data)
except Exception as e:
    print(e)