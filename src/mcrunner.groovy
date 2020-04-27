def output = new StringBuffer()
def error = new StringBuffer()
def initialMemory = '-Xms2048M'
def maximumMemory = '-Xmx8192M'
def forgeVersion = 'forge-1.15.2-31.1.46.jar'

def launchForge = "cmd /c java $initialMemory $maximumMemory -jar $forgeVersion"
def proc = launchForge.execute()

assert output instanceof StringBuffer
assert error instanceof StringBuffer
assert initialMemory instanceof String
assert initialMemory == '-Xms2048M'
assert maximumMemory instanceof String
assert maximumMemory == '-Xmx8192M'
assert forgeVersion instanceof String
assert forgeVersion == 'forge-1.15.2-31.1.46.jar'
assert launchForge instanceof GString
assert launchForge == "cmd /c java $initialMemory $maximumMemory -jar $forgeVersion"

proc.consumeProcessOutput(output, error)
proc.waitForOrKill(10000)
print output