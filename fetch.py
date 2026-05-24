import urllib.request
url = "https://maven.terraformersmc.com/releases/com/terraformersmc/modmenu/maven-metadata.xml"
print(urllib.request.urlopen(url).read().decode('utf-8'))