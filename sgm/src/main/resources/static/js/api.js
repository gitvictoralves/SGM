/**
 * api.js — camada única de comunicação com a API do SGM.
 * Injeta o token JWT em toda requisição, trata erros no formato
 * de ErroResponse do back-end e redireciona para o login em caso de sessão expirada.
 */
const SGM = (() => {
  const BASE_URL = window.location.origin;

  function getToken() {
    return sessionStorage.getItem('sgm_token');
  }

  async function request(path, options = {}) {
    const token = getToken();
    const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
    if (token) headers['Authorization'] = `Bearer ${token}`;

    let response;
    try {
      response = await fetch(`${BASE_URL}${path}`, { ...options, headers });
    } catch (err) {
      throw new Error('Não foi possível conectar ao servidor. Verifique se a API está no ar.');
    }

    // Sessão expirada ou inválida
    if (response.status === 401 && !path.startsWith('/auth/')) {
      if (window.SGMAuth) window.SGMAuth.logout();
      window.location.href = 'login.html';
      throw new Error('Sessão expirada. Faça login novamente.');
    }

    if (response.status === 204) return null;

    const contentType = response.headers.get('content-type') || '';
    const isJson = contentType.includes('application/json');
    const data = isJson ? await response.json().catch(() => null) : null;

    if (!response.ok) {
      const mensagem = (data && (data.mensagem || data.erro)) || `Erro inesperado (${response.status}).`;
      const erro = new Error(mensagem);
      erro.status = response.status;
      erro.payload = data;
      throw erro;
    }

    return data;
  }

  return {
    get: (path) => request(path, { method: 'GET' }),
    post: (path, body) => request(path, { method: 'POST', body: JSON.stringify(body) }),
    put: (path, body) => request(path, { method: 'PUT', body: JSON.stringify(body) }),
    patch: (path, body) => request(path, { method: 'PATCH', body: JSON.stringify(body) }),
    del: (path) => request(path, { method: 'DELETE' }),
  };
})();
