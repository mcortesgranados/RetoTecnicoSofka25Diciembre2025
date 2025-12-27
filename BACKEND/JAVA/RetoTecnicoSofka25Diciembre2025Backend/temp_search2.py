import zipfile
from pathlib import Path
cache = Path('C:/Users/Administrator/.gradle/caches/modules-2/files-2.1')
for jar in cache.rglob('*springdoc-openapi-starter-webmvc-ui-3.0.0*.jar'):
    with zipfile.ZipFile(jar) as z:
        for name in z.namelist():
            if name.endswith('spring-configuration-metadata.json'):
                data = z.read(name).decode('utf-8', errors='ignore')
                if 'primaryName' in data:
                    print(jar)
                    print(name)
                    print('\n'.join([line for line in data.splitlines() if 'primaryName' in line]))
