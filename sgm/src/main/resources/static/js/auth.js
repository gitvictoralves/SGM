/**
 * auth.js — sessão do usuário (token JWT + dados básicos) e proteção de páginas internas.
 */
const SGMAuth = (() => {
  const TOKEN_KEY = 'sgm_token';
  const USER_KEY = 'sgm_user';

  function saveSession({ token, email, nome }) {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, JSON.stringify({ email, nome }));
  }

  function getUser() {
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) return null;
    try {
      return JSON.parse(raw);
    } catch {
      return null;
    }
  }

  function isAuthenticated() {
    return !!localStorage.getItem(TOKEN_KEY);
  }

  function logout() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  }

  /** Bloqueia páginas internas: redireciona para o login se não houver sessão. */
  function requireAuth() {
    if (!isAuthenticated()) {
      window.location.href = 'login.html';
    }
  }

  /**
   * Preenche o nome do usuário na topbar e liga o botão "Sair" de páginas
   * que usam o layout padrão (sidebar + topbar).
   */
  function wireShell() {
    const user = getUser();
    const nameEl = document.getElementById('userName');
    if (nameEl) nameEl.textContent = user?.nome || user?.email || 'Usuário';

    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
      logoutBtn.addEventListener('click', () => {
        logout();
        window.location.href = 'login.html';
      });
    }
  }

  return { saveSession, getUser, isAuthenticated, logout, requireAuth, wireShell };
})();
