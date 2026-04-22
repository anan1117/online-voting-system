<template>
  <div id="app">
    <header style="background: #009973; padding: 15px; color: white; display: flex; justify-content: space-between; align-items: center;">
      <h1 style="margin: 0; font-size: 1.5rem;">玉山銀行投票系統</h1>
      <nav>
        <router-link to="/" style="color: white; margin-right: 15px; text-decoration: none;">我要投票</router-link>
        <router-link to="/admin" style="color: white; text-decoration: none;">管理後台</router-link>
      </nav>
    </header>

    <router-view />
  </div>
</template>

<style>
/* 增加一些簡單的導航樣式 */
nav a.router-link-active {
  font-weight: bold;
  text-decoration: underline !important;
}
</style>