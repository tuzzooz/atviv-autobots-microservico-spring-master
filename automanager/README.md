

Pré-requisitos
- Java 17
- Maven (opcional, o wrapper `mvnw.cmd` já está no projeto)

Local do projeto
- pasta do serviço: `automanager`

1) Build (gerar JAR)
Abra PowerShell na pasta `automanager` e rode:
```powershell
cd 'automanager'
.\mvnw.cmd clean package -DskipTests
```

2) Rodar a aplicação
Opções:
- Via Maven (ver logs no terminal):
```powershell
.\mvnw.cmd spring-boot:run
```
- Ou com o JAR gerado (background):
```powershell
Start-Process -FilePath 'java' -ArgumentList '-jar','target\automanager-0.0.1-SNAPSHOT.jar','--server.port=8081' -NoNewWindow -PassThru
```
Observação: o projeto já foi configurado para rodar em `server.port=8081` no `application.properties`. Se preferir outra porta, passe `--server.port=<porta>`.

3) Testes manuais com PowerShell (exemplos)
- Login e capturar token (header `Authorization`):
```powershell
$body = '{ "nomeUsuario":"admin", "senha":"123456" }'
$res = Invoke-WebRequest -Method Post -Uri 'http://localhost:8081/login' -Body $body -ContentType 'application/json' -UseBasicParsing
$token = $res.Headers['Authorization']   # contém "Bearer <token>"
Write-Output $token
```
- Usar token para listar usuários (apenas ADMIN):
```powershell
$h = @{ Authorization = $token }
Invoke-RestMethod -Method Get -Uri 'http://localhost:8081/obter-usuarios' -Headers $h
```
- Cadastrar cliente (requer ADMIN):
```powershell
$body = @{ nome = 'ClienteTeste' } | ConvertTo-Json
Invoke-WebRequest -Method Post -Uri 'http://localhost:8081/cadastrar-cliente' -Headers $h -Body $body -ContentType 'application/json' -UseBasicParsing
```
- Listar clientes (ADMIN, GERENTE, VENDEDOR):
```powershell
Invoke-RestMethod -Method Get -Uri 'http://localhost:8081/obter-clientes' -Headers $h
```

4) Rotas úteis (Thunder Client)
- POST  `/login` — Body JSON: `{ "nomeUsuario":"admin", "senha":"123456" }` (retorna header `Authorization`)
- POST  `/cadastrar-usuario` — Body JSON: `{ "nome": "Maria", "credencial": { "nomeUsuario": "maria", "senha": "abc" }}` (público)
- GET   `/obter-usuarios` — HEADER `Authorization: Bearer <token>` (somente `ROLE_ADMIN`)
- POST  `/cadastrar-cliente` — HEADER `Authorization: Bearer <token>` + Body `{ "nome": "ClienteTeste" }` (ADMIN)
- GET   `/obter-clientes` — HEADER `Authorization: Bearer <token>` (ADMIN|GERENTE|VENDEDOR)

5) Thunder Client — coleção pronta
Arquivo gerado: `thunder_client_automanager_collection.json`
- Caminho: `automanager/thunder_client_automanager_collection.json`
- Importe este arquivo no Thunder Client (Collections → Import) e configure o `Environment`:
  - variável `baseUrl` = `http://localhost:8081`
  - variável `token` (preencher após executar login)

6) Observações e debug
- Se receber `403 Forbidden` ao acessar um endpoint protegido:
  - Verifique se está enviando o header `Authorization` com o valor `Bearer <token>` (incluindo o `Bearer `).
  - Verifique se o token foi gerado com um usuário que possui a role necessária (ex.: `admin` para `/obter-usuarios`).
  - Tokens expiraram? `jwt.expiration` no `application.properties` está em milissegundos (padrão no projeto: `600000` = 10 minutos).
- Logs aparecem no terminal quando a aplicação é iniciada via Maven; verifique para mensagens de erro.

7) Parar a aplicação
- Se rodou em foreground (mvn spring-boot:run): Ctrl+C no terminal.
- Se iniciou com `Start-Process`, mate o processo Java:
```powershell
Get-Process java | Where-Object { $_.Path -like '*automanager*' } | Stop-Process -Force
```

