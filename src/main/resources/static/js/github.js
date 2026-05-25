async function fetchGitHubProfile(username) {
  try {
    const res = await fetch(`https://api.github.com/users/${username}`);
    if (!res.ok) {
      console.warn(`GitHub API: ${res.status} for ${username}`);
      return null;
    }
    const data = await res.json();
    return {
      avatar_url: data.avatar_url,
      name: data.name || username,
      bio: data.bio || "",
      html_url: data.html_url,
      followers: data.followers ?? 0,
      following: data.following ?? 0,
      public_repos: data.public_repos ?? 0,
    };
  } catch (err) {
    console.warn("GitHub fetch error:", err);
    return null;
  }
}

async function renderTeamCards(containerId, members) {
  const container = document.getElementById(containerId);

  if (!container) return;

  container.innerHTML = `
    <div class="flex flex-col items-center justify-center py-10 text-base-content/50">
      <span class="loading loading-spinner loading-md"></span>
      <p class="mt-3 text-sm">Loading profil tim...</p>
    </div>
  `;

  const cards = await Promise.all(
    members.map(async (member) => {
      const profile = await fetchGitHubProfile(member.username);

      return {
        ...member,
        profile,
      };
    }),
  );

  container.innerHTML = "";

  cards.forEach((member) => {
    const wrapper = document.createElement("div");

    if (member.profile) {
      wrapper.innerHTML = `
        <a href="${member.profile.html_url}" class="border-b border-base-200 py-4">
          <div class="flex items-center justify-between gap-4">

            <!-- LEFT -->
            <div class="flex items-center gap-4 min-w-0 flex-1">

              <!-- Avatar -->
                <img
  src="${member.profile.avatar_url}"
  alt="${member.profile.name}"
  loading="lazy"
  style="height: 60px;"
  class=" rounded-full object-cover"
/>

              <!-- Info -->
              <div class="min-w-0">
                <a
                  href="${member.profile.html_url}"
                  target="_blank"
                  rel="noopener noreferrer"
                  class="block"
                >
                  <h4 class="font-medium text-sm sm:text-base truncate">
                    ${member.profile.name}
                  </h4>
                </a>

                <p class="text-sm text-base-content/60 truncate">
                  ${member.profile.bio || "No bio available."}
                </p>
              </div>

            </div>

            <!-- RIGHT -->
            <div class="shrink-0">
              <span class="text-xs sm:text-sm text-base-content/50">
                ${member.role}
              </span>
            </div>

          </div>

        </a>
      `;
    } else {
      wrapper.innerHTML = `
        <div class="border-b border-base-200 py-4">

          <div class="flex items-center justify-between gap-4">

            <!-- LEFT -->
            <div class="flex items-center gap-4 min-w-0 flex-1">

              <!-- Fallback Avatar -->
              <div
  style="height: 60px;"
                class="rounded-full bg-primary/10 text-primary flex items-center justify-center text-sm font-semibold shrink-0"
              >
                ${member.role.charAt(0)}
              </div>

              <!-- Info -->
              <div class="min-w-0">
                <h4 class="font-medium text-sm sm:text-base truncate">
                  ${member.displayName || member.role}
                </h4>

                <p class="text-sm text-base-content/60 truncate">
                  Team member
                </p>
              </div>

            </div>

            <!-- RIGHT -->
            <div class="shrink-0">
              <span class="text-xs sm:text-sm text-base-content/50">
                ${member.role}
              </span>
            </div>

          </div>

        </div>
      `;
    }

    container.appendChild(wrapper);
  });
}
