// キャッシュ名とキャッシュ対象の指定
var CACHE_NAME = 'sprandoom1-caches';
var urlsToCache = [
  '/sprandoom1/'
];

// インストール処理
self.addEventListener('install', function(e) {
  e.waitUntil(
    caches
      .open(CACHE_NAME)
      .then(function(c) {
        return c.addAll(urlsToCache);
      })
  );
});

// リソースフェッチ時のキャッシュロード処理
self.addEventListener('fetch', function(e) {
  e.respondWith(
    caches
      .match(e.request)
      .then(function(res) {
        return res ? res : fetch(e.request);
      })
  );
});